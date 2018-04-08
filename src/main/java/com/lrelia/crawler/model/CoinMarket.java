package com.lrelia.crawler.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/8
 */
public class CoinMarket extends CoinMarketCny {
    private String id;
    private String name;
    private String symbol;
    private String rank;
    private String price_usd;
    private String price_btc;

    @SerializedName("24h_volume_usd")
    private String twenty_forth_volume_usd;

    private String market_cap_usd;
    private String available_supply;
    private String total_supply;
    private String max_supply;
    @SerializedName("percent_change_1h")
    private String percent_change_one_h;
    @SerializedName("percent_change_24h")
    private String percent_change_twenty_forth_h;
    @SerializedName("percent_change_7d")
    private String percent_change_twenty_seven_d;
    private String last_updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(String price_usd) {
        this.price_usd = price_usd;
    }

    public String getPrice_btc() {
        return price_btc;
    }

    public void setPrice_btc(String price_btc) {
        this.price_btc = price_btc;
    }

    public String getTwenty_forth_volume_usd() {
        return twenty_forth_volume_usd;
    }

    public void setTwenty_forth_volume_usd(String twenty_forth_volume_usd) {
        this.twenty_forth_volume_usd = twenty_forth_volume_usd;
    }

    public String getMarket_cap_usd() {
        return market_cap_usd;
    }

    public void setMarket_cap_usd(String market_cap_usd) {
        this.market_cap_usd = market_cap_usd;
    }

    public String getAvailable_supply() {
        return available_supply;
    }

    public void setAvailable_supply(String available_supply) {
        this.available_supply = available_supply;
    }

    public String getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(String total_supply) {
        this.total_supply = total_supply;
    }

    public String getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(String max_supply) {
        this.max_supply = max_supply;
    }

    public String getPercent_change_one_h() {
        return percent_change_one_h;
    }

    public void setPercent_change_one_h(String percent_change_one_h) {
        this.percent_change_one_h = percent_change_one_h;
    }

    public String getPercent_change_twenty_forth_h() {
        return percent_change_twenty_forth_h;
    }

    public void setPercent_change_twenty_forth_h(String percent_change_twenty_forth_h) {
        this.percent_change_twenty_forth_h = percent_change_twenty_forth_h;
    }

    public String getPercent_change_twenty_seven_d() {
        return percent_change_twenty_seven_d;
    }

    public void setPercent_change_twenty_seven_d(String percent_change_twenty_seven_d) {
        this.percent_change_twenty_seven_d = percent_change_twenty_seven_d;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }
}
