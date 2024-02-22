package dev.dexuby.leagueclient4j.api;

public record RerollPoints(int currentPoints,
                           int maxRolls,
                           int numberOfRolls,
                           int pointsCostToRoll,
                           int pointsToReroll) {
}
