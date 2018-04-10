package com.lrelia.crawler.controller;

import com.google.gson.Gson;
import com.lrelia.crawler.repository.TokenTransferHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/10
 */
@RestController
public class CalculateController {
    @Autowired
    TokenTransferHistoryRepository tokenTransferHistoryRepository;

    @GetMapping(value = "calculate/income")
    @ResponseBody
    public String addressList() {
//        List<EtherscanAddress> addresses = addressRepository.findAll();
//        return new Gson().toJson(addresses);
    return "";
    }
}
