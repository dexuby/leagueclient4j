package dev.dexuby.leagueclient4j;

import com.google.gson.JsonObject;
import dev.dexuby.easycommon.external.jetbrains.annotations.NotNull;
import dev.dexuby.leagueclient4j.api.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LeagueClientAPI {

    private final HttpClient httpClient;
    private final RequestData requestData;

    protected LeagueClientAPI(@NotNull final RequestData requestData) {

        try {
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{TrustAllTrustManager.getInstance()}, new SecureRandom());
            this.httpClient = HttpClient.newBuilder().sslContext(sslContext).build();
        } catch (final NoSuchAlgorithmException | KeyManagementException ex) {
            throw new RuntimeException(ex);
        }

        this.requestData = requestData;

    }

    @NotNull
    public CompletableFuture<ChatParticipants> getParticipants() {

        final HttpRequest request = this.createGetRequest("/chat/v5/participants", this.requestData.riotPort(), this.requestData.riotToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, ChatParticipants.class));

    }

    @NotNull
    public CompletableFuture<RegionLocale> getRegionLocale() {

        final HttpRequest request = this.createGetRequest("/riotclient/region-locale", this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, RegionLocale.class));

    }

    @NotNull
    public CompletableFuture<Wallet> getWallet() {

        final HttpRequest request = this.createGetRequest("/lol-inventory/v1/wallet?currencyTypes=[%22RP%22,%22lol_blue_essence%22]", this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, Wallet.class));

    }

    @NotNull
    public CompletableFuture<LoginSession> getSession() {

        final HttpRequest request = this.createGetRequest("/lol-login/v1/session", this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, LoginSession.class));

    }

    @NotNull
    public CompletableFuture<Summoner> getCurrentSummoner() {

        final HttpRequest request = this.createGetRequest("/lol-summoner/v1/current-summoner", this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, Summoner.class));

    }

    @NotNull
    public CompletableFuture<Boolean> isEligibleForFreeRiotAlias() {

        final HttpRequest request = this.createGetRequest("/lol-summoner/v1/riot-alias-free-eligibility", this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Boolean::parseBoolean);

    }

    @NotNull
    public CompletableFuture<InviteResponse[]> inviteToLobby(@NotNull final List<Long> summonerIds) {

        final StringBuilder stringBuilder = new StringBuilder("[");
        for (final long summonerId : summonerIds) {
            if (stringBuilder.length() != 1)
                stringBuilder.append(",");
            stringBuilder.append("{\"toSummonerId\":")
                    .append(summonerId)
                    .append("}");
        }
        stringBuilder.append("]");

        final HttpRequest request = this.createPostRequest(
                "/lol-lobby/v2/lobby/invitations",
                this.requestData.clientPort(),
                this.requestData.clientToken(),
                stringBuilder.toString()
        );
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, InviteResponse[].class));

    }

    /**
     * Creates a new lobby.
     *
     * @param queueId The queue id.
     * @return CompletableFuture of type JsonObject.
     */

    @NotNull
    public CompletableFuture<JsonObject> createLobby(final int queueId) {

        final HttpRequest request = this.createPostRequest("/lol-lobby/v2/lobby", this.requestData.clientPort(), this.requestData.clientToken(), String.format("{\"queueId\":%d}", queueId));
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, JsonObject.class));

    }

    /**
     * Delete the active lobby.
     *
     * @return CompletableFuture of type Void.
     */

    public CompletableFuture<Void> deleteLobby() {

        final HttpRequest request = this.createDeleteRequest("/lol-lobby/v2/lobby", this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding()).thenAccept(HttpResponse::body);

    }

    /**
     * Starts the matchmaking queue.
     *
     * @return CompletableFuture of type Void.
     */

    public CompletableFuture<Void> startQueue() {

        final HttpRequest request = this.createPostRequest("/lol-lobby/v2/lobby/matchmaking/search", this.requestData.clientPort(), this.requestData.clientToken(), "");
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding()).thenAccept(HttpResponse::body);

    }

    /**
     * Cancels the active matchmaking queue.
     *
     * @return CompletableFuture of type Void.
     */

    public CompletableFuture<Void> cancelQueue() {

        final HttpRequest request = this.createDeleteRequest("/lol-lobby/v2/lobby/matchmaking/search", this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding()).thenAccept(HttpResponse::body);

    }

    public CompletableFuture<Summoner[]> findSummonersByAliases(@NotNull final List<AccountName> accountNames) {

        final HttpRequest request = this.createPostRequest("/lol-summoner/v1/summoners/aliases", this.requestData.clientPort(), this.requestData.clientToken(), Constants.GSON.toJson(accountNames));
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, Summoner[].class));

    }

    public CompletableFuture<Summoner> findSummonerBySummonerId(final int summonerId) {

        final HttpRequest request = this.createGetRequest("/lol-summoner/v1/summoners/" + summonerId, this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, Summoner.class));

    }

    public CompletableFuture<Summoner> findSummonerByPUUID(@NotNull final String puuid) {

        final HttpRequest request = this.createGetRequest("/lol-summoner/v2/summoners/puuid/" + puuid, this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(response -> Constants.GSON.fromJson(response, Summoner.class));

    }

    /**
     * Accepts the active ready check.
     *
     * @return CompletableFuture of type Void.
     */

    public CompletableFuture<Void> acceptReadyCheck() {

        final HttpRequest request = this.createPostRequest("/lol-matchmaking/v1/ready-check/accept", this.requestData.clientPort(), this.requestData.clientToken(), "");
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding()).thenAccept(HttpResponse::body);

    }

    /**
     * Declines the active ready check.
     *
     * @return CompletableFuture of type Void.
     */

    public CompletableFuture<Void> declineReadyCheck() {

        final HttpRequest request = this.createPostRequest("/lol-matchmaking/v1/ready-check/decline", this.requestData.clientPort(), this.requestData.clientToken(), "");
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding()).thenAccept(HttpResponse::body);

    }

    /**
     * Re-rolls the active champion. (ARAM)
     *
     * @return CompletableFuture of type Void.
     */

    public CompletableFuture<Void> rerollChampion() {

        final HttpRequest request = this.createPostRequest("/lol-champ-select/v1/session/my-selection/reroll", this.requestData.clientPort(), this.requestData.clientToken(), "");
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding()).thenAccept(HttpResponse::body);

    }

    /**
     * Report a summoner.
     *
     * @param reportRequest The report request.
     * @return CompletableFuture of type Void.
     */

    public CompletableFuture<Void> report(@NotNull final ReportRequest reportRequest) {

        final HttpRequest request = this.createGetRequest("/lol-player-report-sender/v1/in-game-reports", this.requestData.clientPort(), this.requestData.clientToken());
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenRun(() -> {
                    final HttpRequest actualRequest = this.createPostRequest("/lol-player-report-sender/v1/end-of-game-reports", this.requestData.clientPort(), this.requestData.clientToken(), Constants.GSON.toJson(reportRequest));
                    this.httpClient.sendAsync(actualRequest, HttpResponse.BodyHandlers.discarding());
                });

    }

    @NotNull
    public CompletableFuture<Void> fakeRank() {

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://127.0.0.1:" + this.requestData.clientPort() + "/lol-chat/v1/me"))
                .headers(
                        "Authorization", "Basic " + this.requestData.clientToken(),
                        "Content-Type", "application/json"
                )
                .PUT(HttpRequest.BodyPublishers.ofString("{\"lol\":{\"rankedLeagueQueue\":\"RANKED_SOLO_5x5\",\"rankedLeagueTier\":\"CHALLENGER\",\"rankedLeagueDivision\":\"I\"}}"))
                .build();
        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding()).thenAccept(HttpResponse::body);

    }

    protected HttpRequest createGetRequest(@NotNull final String endpoint, @NotNull final String port, @NotNull final String token) {

        return HttpRequest.newBuilder()
                .uri(URI.create("https://127.0.0.1:" + port + endpoint))
                .headers(
                        "Authorization", "Basic " + token,
                        "Content-Type", "application/json"
                )
                .GET()
                .build();

    }

    protected HttpRequest createPostRequest(@NotNull final String endpoint, @NotNull final String port, @NotNull final String token, @NotNull final String content) {

        return HttpRequest.newBuilder()
                .uri(URI.create("https://127.0.0.1:" + port + endpoint))
                .headers(
                        "Authorization", "Basic " + token,
                        "Content-Type", "application/json"
                )
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .build();

    }

    protected HttpRequest createDeleteRequest(@NotNull final String endpoint, @NotNull final String port, @NotNull final String token) {

        return HttpRequest.newBuilder()
                .uri(URI.create("https://127.0.0.1:" + port + endpoint))
                .headers(
                        "Authorization", "Basic " + token,
                        "Content-Type", "application/json"
                )
                .DELETE()
                .build();

    }

}
