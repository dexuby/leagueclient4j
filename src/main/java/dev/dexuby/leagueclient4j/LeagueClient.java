package dev.dexuby.leagueclient4j;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;
import dev.dexuby.easycommon.external.jetbrains.annotations.Nullable;
import dev.dexuby.leagueclient4j.api.RequestData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class LeagueClient {

    private static class LeagueClientSingleton {

        private static final LeagueClient INSTANCE = new LeagueClient();

    }

    private LeagueClient() {

    }

    /**
     * Create a new league client API instance by fetching all the necessary data from the running process.
     *
     * @return The created league client API instance.
     */

    public LeagueClientAPI hookAPI() {

        final ProcessHandle processHandle = this.findLeagueProcessHandle();
        if (processHandle == null) {
            Constants.LOGGER.error("Failed to find LeagueClientUx.exe");
            return null;
        }

        final String commandLine = this.getCommandLine(processHandle).orElse(null);
        if (commandLine == null) {
            Constants.LOGGER.error("Failed to fetch command line.");
            return null;
        }

        final Map<String, String> arguments = this.extractArguments(commandLine);
        final String riotPort = arguments.get("--riotclient-app-port");
        final String riotToken = Base64.getEncoder().encodeToString(("riot:" + arguments.get("--riotclient-auth-token")).getBytes(StandardCharsets.ISO_8859_1));
        final String clientPort = arguments.get("--app-port");
        final String clientToken = Base64.getEncoder().encodeToString(("riot:" + arguments.get("--remoting-auth-token")).getBytes(StandardCharsets.ISO_8859_1));

        return new LeagueClientAPI(new RequestData(riotPort, riotToken, clientPort, clientToken));

    }

    /**
     * Find the league client process handle.
     *
     * @return The process handle or <code>null</code>.
     */

    @Nullable
    private ProcessHandle findLeagueProcessHandle() {

        return ProcessHandle.allProcesses()
                .filter(handle -> handle.info().command().orElse("").endsWith(Constants.LEAGUE_CLIENT_EXECUTABLE))
                .findFirst()
                .orElse(null);

    }

    /**
     * Get command line on windows.
     *
     * @param processHandle The process handle.
     * @return Optional containing the command line or nothing if not found.
     */

    private Optional<String> getCommandLine(@NotNull final ProcessHandle processHandle) {

        if (!this.isWindows())
            return processHandle.info().commandLine();

        try {
            final Process process = new ProcessBuilder("wmic", "process", "where", "ProcessID=" + processHandle.pid(), "get",
                    "commandline", "/format:list").
                    redirectErrorStream(true).
                    start();
            try (final InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                 final BufferedReader reader = new BufferedReader(inputStreamReader)) {
                while (true) {
                    final String line = reader.readLine();
                    if (line == null)
                        return Optional.empty();
                    if (!line.startsWith("CommandLine="))
                        continue;
                    return Optional.of(line.substring("CommandLine=".length()));
                }
            }
        } catch (final IOException ex) {
            Constants.LOGGER.error("Failed to fetch command line:", ex);
            return Optional.empty();
        }

    }

    /**
     * Extracts all arguments from the provided command line.
     *
     * @param commandLine The command line.
     * @return The extracted arguments as a map.
     */

    private Map<String, String> extractArguments(@NotNull final String commandLine) {

        final Map<String, String> arguments = new HashMap<>();
        final StringBuilder stringBuilder = new StringBuilder();
        boolean read = false;
        for (final char c : commandLine.toCharArray()) {
            if (c == '"') {
                if (read) {
                    read = false;
                    final String argument = stringBuilder.toString();
                    final int firstIndex = argument.indexOf('=');
                    if (firstIndex != -1)
                        arguments.put(argument.substring(0, firstIndex), argument.substring(firstIndex + 1));
                    stringBuilder.setLength(0);
                } else {
                    read = true;
                }
            } else {
                if (read)
                    stringBuilder.append(c);
            }
        }

        return arguments;

    }

    /**
     * Determines if the program is running on Windows.
     *
     * @return <code>true</code> if it's running on Windows, <code>false</code> otherwise.
     */

    private boolean isWindows() {

        return System.getProperty("os.name").startsWith("Windows");

    }

    /**
     * Returns the singleton instance.
     *
     * @return The singleton instance.
     */

    public static LeagueClient getInstance() {

        return LeagueClientSingleton.INSTANCE;

    }

}
