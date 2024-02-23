package dev.dexuby.leagueclient4j.api;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;
import dev.dexuby.easycommon.external.jetbrains.annotations.Nullable;

import java.util.List;

public record ReportRequest(@Nullable String comment,
                            long gameId,
                            @NotNull List<ReportCategory> categories,
                            long offenderSummonerId,
                            @NotNull String offenderPuuid) {
}
