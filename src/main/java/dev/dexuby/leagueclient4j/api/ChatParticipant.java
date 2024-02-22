package dev.dexuby.leagueclient4j.api;

import com.google.gson.annotations.SerializedName;
import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;
import dev.dexuby.easycommon.external.jetbrains.annotations.Nullable;

public record ChatParticipant(@Nullable String activePlatform,
                              @NotNull String cid,
                              @SerializedName("game_name") @NotNull String gameName,
                              @SerializedName("game_tag") @NotNull String gameTag,
                              boolean muted,
                              @NotNull String name,
                              @NotNull String pid,
                              @NotNull String puuid,
                              @NotNull String region) {
}
