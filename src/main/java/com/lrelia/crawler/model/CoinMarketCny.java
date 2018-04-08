package com.lrelia.crawler.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/8
 */
public class CoinMarketCny {

    private String price_cny;
    @SerializedName("24h_volume_cny")
    private String twenty_forth_volume_cny;

    private String market_cap_cny;

    public String getPrice_cny() {
        return price_cny;
    }

    public void setPrice_cny(String price_cny) {
        this.price_cny = price_cny;
    }

    public String getTwenty_forth_volume_cny() {
        return twenty_forth_volume_cny;
    }

    public void setTwenty_forth_volume_cny(String twenty_forth_volume_cny) {
        this.twenty_forth_volume_cny = twenty_forth_volume_cny;
    }

    public String getMarket_cap_cny() {
        return market_cap_cny;
    }

    public void setMarket_cap_cny(String market_cap_cny) {
        this.market_cap_cny = market_cap_cny;
    }



}
