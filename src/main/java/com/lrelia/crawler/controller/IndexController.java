package com.lrelia.crawler.controller;

import com.lrelia.crawler.init.CoinMarketInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/4
 */
@Controller
public class IndexController {
    @Autowired
    CoinMarketInit coinMarketInit;

    @GetMapping(value = "")
    public String toBotList() {
        coinMarketInit.initSymbol();
        return "index";
    }
}
