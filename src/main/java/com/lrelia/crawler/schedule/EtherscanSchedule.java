package com.lrelia.crawler.schedule;

import com.lrelia.crawler.repository.EtherscanAddressRepository;
import com.lrelia.crawler.repository.TokenTransferHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/4
 */
@Component
public class EtherscanSchedule {

    @Autowired
    private TokenTransferHistoryRepository tokenTransferHistoryRepository;

    @Autowired
    private EtherscanAddressRepository addressRepository;

    @Scheduled(cron = "*/10 * * * * ? ")
    public void test() {

    }


}
