package dev.dexuby.leagueclient4j.api;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;

public record InviteResponse(@NotNull String invitationId,
                             @NotNull String invitationType,
                             @NotNull String state,
                             @NotNull String timestamp,
                             long toSummonerId,
                             @NotNull String toSummonerName) {
}
