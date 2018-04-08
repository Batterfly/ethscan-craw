package com.lrelia.crawler.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/5
 */
public class TxCache {
    /**
     * k-v address-tx
     */
    public static Map<String, String> cache = new ConcurrentHashMap<String, String>();

    public static void setCache(String key, String obj) {
        cache.put(key, obj);
    }

    public static String getCache(String key) {
        return cache.get(key);
    }

    public static void removeCache(String key) {
        cache.remove(key);
    }
}
