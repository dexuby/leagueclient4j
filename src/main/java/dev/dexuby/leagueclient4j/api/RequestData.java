package dev.dexuby.leagueclient4j.api;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;

public record RequestData(@NotNull String riotPort,
                          @NotNull String riotToken,
                          @NotNull String clientPort,
                          @NotNull String clientToken) {
}
