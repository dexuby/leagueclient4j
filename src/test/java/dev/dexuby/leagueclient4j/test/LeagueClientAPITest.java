package dev.dexuby.leagueclient4j.test;

import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;
import dev.dexuby.leagueclient4j.LeagueClient;
import dev.dexuby.leagueclient4j.LeagueClientAPI;

import java.util.List;

/**
 * No real tests for now, just a simple class to quickly test a few features.
 */

public class LeagueClientAPITest {

    public static void main(@NotNull final String[] args) {

        final LeagueClientAPI leagueClientAPI = LeagueClient.getInstance().hookAPI();
        if (leagueClientAPI == null) return;

        leagueClientAPI.getParticipants().thenAccept(System.out::println).join();
        leagueClientAPI.getRegionLocale().thenAccept(System.out::println).join();
        leagueClientAPI.getWallet().thenAccept(System.out::println).join();
        leagueClientAPI.getSession().thenAccept(System.out::println).join();
        leagueClientAPI.getCurrentSummoner().thenAccept(System.out::println).join();
        leagueClientAPI.isEligibleForFreeRiotAlias().thenAccept(System.out::println).join();
        leagueClientAPI.inviteToLobby(List.of(123L)).thenAccept(System.out::println).join();
        leagueClientAPI.fakeRank().join();

    }

}
