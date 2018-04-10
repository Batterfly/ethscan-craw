package com.lrelia.crawler.schedule;

import com.lrelia.crawler.async.AsyncSaveDbService;
import com.lrelia.crawler.entity.EtherscanAddress;
import com.lrelia.crawler.repository.EtherscanAddressRepository;
import com.lrelia.crawler.thread.AltTokenCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/10
 */
@Component
@Configuration
public class AltSchedule {

    private static ExecutorService exec;

    @Autowired
    private AsyncSaveDbService asyncSaveDbService;

    @Autowired
    private EtherscanAddressRepository addressRepository;

    @Scheduled(cron = "0/10 * * * * *")
    public void fetchAlt() {
        exec = Executors.newFixedThreadPool(10);
        List<EtherscanAddress> addressList = addressRepository.findAll();
        for (EtherscanAddress address : addressList) {
            exec.execute(new AltTokenCrawler(address, asyncSaveDbService));
        }
        exec.shutdown();
    }
}
