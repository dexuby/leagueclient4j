package dev.dexuby.leagueclient4j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Constants {

    public static final Logger LOGGER = LoggerFactory.getLogger("leaguebreach");
    public static final String LEAGUE_CLIENT_EXECUTABLE = "LeagueClientUx.exe";
    public static final Gson GSON = new GsonBuilder()
            .create();

}
