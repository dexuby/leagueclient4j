package dev.dexuby.leagueclient4j.api;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;

public record AccountName(@NotNull String gameName,
                          @NotNull String tagLine) {
}
