package dev.dexuby.leagueclient4j.api;

import com.google.gson.annotations.SerializedName;

public record Wallet(@SerializedName("lol_blue_essence") int lolBlueEssence,
                     @SerializedName("RP") int rp) {
}
