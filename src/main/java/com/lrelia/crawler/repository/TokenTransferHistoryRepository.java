package com.lrelia.crawler.repository;

import com.lrelia.crawler.entity.TokenTransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/5
 */
@Repository
public interface TokenTransferHistoryRepository extends CrudRepository<TokenTransferHistory, Long>, JpaRepository<TokenTransferHistory, Long>, JpaSpecificationExecutor<TokenTransferHistory> {

    @Query(value = "select distinct(token_transfer_history.symbol) from token_transfer_history where token_transfer_history.address_id=?1",nativeQuery = true)
    List<String> findSymbolByAddressId(long addressId);
}