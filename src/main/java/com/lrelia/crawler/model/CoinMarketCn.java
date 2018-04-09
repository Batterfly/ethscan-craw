package com.lrelia.crawler.model;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/9
 */
public class CoinMarketCn {
    private String symbol;
    private String price_usd;
    private String price_btc;
    private String market_cap_usd;
    private String available_supply;
    private String total_supply;
    private String twenty_forth_volume_usd;
    private String percent_change_twenty_forth_h;
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public String getTwenty_forth_volume_usd() {
        return twenty_forth_volume_usd;
    }

    public void setTwenty_forth_volume_usd(String twenty_forth_volume_usd) {
        this.twenty_forth_volume_usd = twenty_forth_volume_usd;
    }

    public String getPercent_change_twenty_forth_h() {
        return percent_change_twenty_forth_h;
    }

    public void setPercent_change_twenty_forth_h(String percent_change_twenty_forth_h) {
        this.percent_change_twenty_forth_h = percent_change_twenty_forth_h;
    }



}
