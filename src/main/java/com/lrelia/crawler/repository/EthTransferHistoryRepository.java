package com.lrelia.crawler.repository;

import com.lrelia.crawler.entity.EthTransferHistory;
import com.lrelia.crawler.entity.TokenTransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/5
 */
@Repository
public interface EthTransferHistoryRepository extends CrudRepository<EthTransferHistory, Long>, JpaRepository<EthTransferHistory, Long>, JpaSpecificationExecutor<EthTransferHistory> {

    @Query(value = " SELECT * " +
            "    FROM eth_transfer_history " +
            "    WHERE address_id = :address_id " +
            "    ORDER BY " +
            "    create_at DESC " +
            "    LIMIT 1", nativeQuery = true)
    EthTransferHistory findLeastEthHistory(@Param("address_id") long addressId);


    @Query(value = "SELECT SUM(eth_transfer_history.`count`),DATE_FORMAT( create_at, \"%Y-%m-%d\" ) FROM eth_transfer_history WHERE " +
            " address_id =?1 AND `type`=?2  AND " +
            "create_at BETWEEN ?3 AND ?4  " +
            "GROUP BY DATE_FORMAT( create_at, \"%Y-%m-%d\" ) ", nativeQuery = true)
    List<Object[]> calculateEthSumByCondition(long addressId, int type, Date startDate, Date endDate);

}
