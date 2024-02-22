package dev.dexuby.leagueclient4j.api;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;

public record RegionLocale(@NotNull String locale,
                           @NotNull String region,
                           @NotNull String webLanguage,
                           @NotNull String webRegion) {
}
