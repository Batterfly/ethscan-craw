package com.lrelia.crawler.controller;

import com.lrelia.crawler.utils.HttpUtil;
import com.lrelia.crawler.utils.LogUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
        return HttpUtil.getString(String.format(priceApi, token_id), 3, null);

    }
}
