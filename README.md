[![](https://jitpack.io/v/dexuby/leagueclient4j.svg)](https://jitpack.io/#dexuby/leagueclient4j)
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.dexuby</groupId>
    <artifactId>leagueclient4j</artifactId>
    <version>1.0.0</version>
</dependency>
```
## Unfinished API for the League of Legends client endpoints of the locally running server

### How to obtain an API instance?
Make sure to start the League of Legends client before you call the #hookAPI method,
the system will fetch all tokens and ports from the running process so you don't have to
do or provide anything manually.
```java
final LeagueClientAPI api = LeagueClient.getInstance().hookAPI();
```

### What's currently possible?
The API provided by this library currently only supports a few things:
- You can fetch all people that you're in a lobby with (even in ranked lobbies where names are hidden): `LeagueClient#getParticipants`
- You can fetch locale data (language, region, etc.): `LeagueClient#getRegionLocale`
- You can fetch your wallet data (RP, blue essence): `LeagueClient#getWallet`
- You can fetch your login session info: `LeagueClient#getSession`
- You can fetch your current summoner data (profile icon id, level, required xp, etc.): `LeagueClient#getCurrentSummoner`
- You can check if your account is eligible for a free riot alias change: `LeagueClient#isEligibleForFreeRiotAlias`
- You can invite people to your current lobby: `LeagueClient#inviteToLobby`
- You can create a new lobby for any active game mode: `LeagueClient#createLobby`
- You can set your Solo & Duo Rank to Challenger (only visible in chat module, not profile): `LeagueClient#fakeRank`

### How do I contribute/find more endpoints?
Easiest way is to use an IFEO debugger to get rid of some of the flags that get applied to the client when it gets started.
Once this is done you can use a tool like Fiddler after enabling HTTPS decryption. If you want to make your own IFEO debugger
I'd recommend to use C# for simplicity, might upload a project for one at a later point. Any implementation you find on GitHub
should work just fine.
