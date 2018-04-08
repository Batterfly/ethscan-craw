package com.lrelia.crawler.init;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lrelia.crawler.entity.CoinMarketMap;
import com.lrelia.crawler.model.CoinMarket;
import com.lrelia.crawler.repository.CoinMarketMapRepository;
import com.lrelia.crawler.utils.HttpUtil;
import com.lrelia.crawler.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/8
 */
public class CoinMarketInit {

    private static final LogUtil logger = LogUtil.newInstance(CoinMarketInit.class);

    private static String coinMarketApi = "https://api.coinmarketcap.com/v1/ticker/?start=0&limit=1800";
    @Autowired
    CoinMarketMapRepository repository;

    public void initSymbol() {
        try {
            String json = HttpUtil.getString(coinMarketApi, 3, null);
            List<CoinMarket> markets = new Gson().fromJson(json, new TypeToken<List<CoinMarket>>() {
            }.getType());
            List<CoinMarketMap> marketMaps = markets.stream().map(m -> {
                CoinMarketMap marketMap = new CoinMarketMap();
                marketMap.setMarketId(m.getId());
                marketMap.setName(m.getName());
                marketMap.setSymbol(m.getSymbol());
                return marketMap;
            }).collect(Collectors.toList());
            marketMaps.stream().filter(distinctByKey(p -> p.getSymbol())).forEach(p ->repository.save(p));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
