package com.lrelia.crawler.controller;

import com.google.gson.Gson;
import com.lrelia.crawler.entity.EtherscanAddress;
import com.lrelia.crawler.repository.EtherscanAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/4
 */
@Controller
public class IndexController {

    @Autowired
    EtherscanAddressRepository addressRepository;

    @GetMapping(value = "")
    public String toBotList() {
        return "index";
    }

    @GetMapping(value = "address")
    @ResponseBody
    public String addressList() {
        List<EtherscanAddress> addresses = addressRepository.findAll();
        return new Gson().toJson(addresses);
    }


}
