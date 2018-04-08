package com.lrelia.crawler.repository;

import com.lrelia.crawler.entity.EthTransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/5
 */
@Repository
public interface EthTransferHistoryRepository extends CrudRepository<EthTransferHistory, Long>, JpaRepository<EthTransferHistory, Long>, JpaSpecificationExecutor<EthTransferHistory> {

}