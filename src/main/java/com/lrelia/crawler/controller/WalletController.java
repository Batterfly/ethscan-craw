package com.lrelia.crawler.controller;

import com.google.gson.Gson;
import com.lrelia.crawler.entity.EtherscanAddress;
import com.lrelia.crawler.entity.TokenTransferHistory;
import com.lrelia.crawler.repository.EtherscanAddressRepository;
import com.lrelia.crawler.repository.TokenTransferHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/10
 */
@RestController
@RequestMapping("wallet")
public class WalletController {
    @Autowired
    TokenTransferHistoryRepository historyRepository;
    @Autowired
    EtherscanAddressRepository etherscanAddressRepository;

    @GetMapping(value = "/tokens/{address_id}")
    public String price(@PathVariable String address_id) {
        List<String> history = historyRepository.findSymbolByAddressId(Long.valueOf(address_id));
        return new Gson().toJson(history);
    }
}
