package com.lrelia.crawler.async;

import com.lrelia.crawler.entity.EthTransferHistory;
import com.lrelia.crawler.entity.TokenTransferHistory;
import com.lrelia.crawler.repository.EthTransferHistoryRepository;
import com.lrelia.crawler.repository.TokenTransferHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/10
 */
@Service
public class AsyncSaveDbService {

    @Autowired
    TokenTransferHistoryRepository altRepository;

    @Autowired
    EthTransferHistoryRepository ethRepositoy;

    @Async
    public void saveTokenHistorys(List<TokenTransferHistory> tokenTransferHistorys) {
        if (CollectionUtils.isEmpty(tokenTransferHistorys)) {
            return;
        }
        altRepository.saveAll(tokenTransferHistorys);
    }

    @Async
    public void saveEthHisory(List<EthTransferHistory> ethTransferHistory) {
        if (CollectionUtils.isEmpty(ethTransferHistory)) {
            return;
        }
        ethRepositoy.saveAll(ethTransferHistory);
    }
}
