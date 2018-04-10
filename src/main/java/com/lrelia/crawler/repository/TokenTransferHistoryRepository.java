package com.lrelia.crawler.repository;

import com.lrelia.crawler.entity.TokenTransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/5
 */
@Repository
public interface TokenTransferHistoryRepository extends CrudRepository<TokenTransferHistory, Long>, JpaRepository<TokenTransferHistory, Long>, JpaSpecificationExecutor<TokenTransferHistory> {

    @Query(value = "select distinct(token_transfer_history.symbol) from token_transfer_history where token_transfer_history.address_id=?1", nativeQuery = true)
    List<String> findSymbolByAddressId(long addressId);

    List<TokenTransferHistory> findByAddressIdAndSymbolAndCreateAtBetween(long addressId, String symbol, Date start, Date end);

    @Query(value = "SELECT SUM(token_transfer_history.`count`),DATE_FORMAT( create_at, \"%Y-%m-%d\" ) FROM token_transfer_history WHERE " +
            " address_id =?1 AND `type`=?2 AND symbol=?3 AND " +
            "create_at BETWEEN ?4 AND ?5  " +
            "GROUP BY DATE_FORMAT( create_at, \"%Y-%m-%d\" ) ", nativeQuery = true)
    List<Object[]> calculateAltTokenSumByCondition(long addressId, int type, String symbol, Date startDate, Date endDate);
}