package dev.dexuby.leagueclient4j.api;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;

public record CurrentSummoner(long accountId,
                              @NotNull String displayName,
                              @NotNull String gameName,
                              @NotNull String internalName,
                              boolean nameChangeFlag,
                              int percentCompleteForNextLevel,
                              @NotNull String privacy,
                              int profileIconId,
                              @NotNull String puuid,
                              @NotNull RerollPoints rerollPoints,
                              int summonerId,
                              int summonerLevel,
                              @NotNull String tagLine,
                              boolean unnamed,
                              int xpSinceLastLevel,
                              int xpUntilNextLevel) {
}
