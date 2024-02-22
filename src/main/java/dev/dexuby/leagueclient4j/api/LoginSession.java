package dev.dexuby.leagueclient4j.api;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;
import dev.dexuby.easycommon.external.jetbrains.annotations.Nullable;

public record LoginSession(long accountId,
                           boolean connected,
                           @Nullable String error,
                           @NotNull String idToken,
                           boolean isInLoginQueue,
                           boolean isNewPlayer,
                           @NotNull String puuid,
                           @NotNull String state,
                           int summonerId,
                           @NotNull String userAuthToken,
                           @NotNull String username) {
}
