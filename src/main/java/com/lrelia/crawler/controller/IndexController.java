package com.lrelia.crawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/4
 */
@Controller
public class IndexController {
    @GetMapping(value = "")
    public String toBotList() {
        return "index";
    }
}
