package dev.dexuby.leagueclient4j.api;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;

import java.util.List;

public record ChatParticipants(@NotNull List<ChatParticipant> participants) {
}
