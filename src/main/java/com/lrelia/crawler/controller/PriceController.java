package com.lrelia.crawler.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lrelia.crawler.model.CoinMarket;
import com.lrelia.crawler.model.CoinMarketCn;
import com.lrelia.crawler.utils.HttpUtil;
import com.lrelia.crawler.utils.LogUtil;
import com.lrelia.crawler.utils.NumberUtil;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/8
 */
@RestController
@RequestMapping("price")
public class PriceController {
    private static final LogUtil logger = LogUtil.newInstance(PriceController.class);

    private static String priceApi = "https://api.coinmarketcap.com/v1/ticker/%s/?convert=CNY";

    @GetMapping(value = "/{token_id}")
    public String price(@PathVariable String token_id) throws IOException {

        logger.info("query token:" + token_id + "price");
        String market = HttpUtil.getString(String.format(priceApi, token_id), 3, null);
        if (StringUtils.isEmpty(market)) return "";
        List<CoinMarket> coinMarkets = new Gson().fromJson(market, new TypeToken<List<CoinMarket>>() {
        }.getType());
        CoinMarket coinMarket = coinMarkets.get(0);
        CoinMarketCn coinMarketCn=new CoinMarketCn();
        coinMarketCn.setSymbol(coinMarket.getSymbol());
        coinMarketCn.setPrice_btc(coinMarket.getPrice_btc());
        coinMarketCn.setPrice_usd(coinMarket.getPrice_usd());
        coinMarketCn.setMarket_cap_usd(NumberUtil.formatTosepara(Float.valueOf(coinMarket.getMarket_cap_usd())));
        coinMarketCn.setTwenty_forth_volume_usd(NumberUtil.formatTosepara(Float.valueOf(coinMarket.getTwenty_forth_volume_usd())));
        coinMarketCn.setAvailable_supply(NumberUtil.formatTosepara(Float.valueOf(coinMarket.getAvailable_supply())));
        coinMarketCn.setTotal_supply(NumberUtil.formatTosepara(Float.valueOf(coinMarket.getTotal_supply())));
        coinMarketCn.setPercent_change_twenty_forth_h(NumberUtil.formatToseparaRemainTwo(Float.valueOf(coinMarket.getPercent_change_twenty_forth_h())));
        return new Gson().toJson(coinMarketCn);
    }
}
