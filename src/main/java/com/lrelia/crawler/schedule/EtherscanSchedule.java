package com.lrelia.crawler.schedule;

import com.google.gson.Gson;
import com.lrelia.crawler.entity.EthTransferHistory;
import com.lrelia.crawler.entity.EtherscanAddress;
import com.lrelia.crawler.model.Txlist;
import com.lrelia.crawler.repository.EthTransferHistoryRepository;
import com.lrelia.crawler.repository.EtherscanAddressRepository;
import com.lrelia.crawler.repository.TokenTransferHistoryRepository;

import com.lrelia.crawler.thread.AltTokenCrawler;
import com.lrelia.crawler.utils.DateUtil;
import com.lrelia.crawler.utils.HttpUtil;
import com.lrelia.crawler.utils.LogUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.lrelia.crawler.utils.DateUtil.parseByTimeStamp;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/4
 */
@Component
public class EtherscanSchedule {

    private static final LogUtil logger = LogUtil.newInstance(EtherscanSchedule.class);

    private static String api = "http://api.etherscan.io/api?module=account&action=txlist&address=%s&startblock=%s&endblock=99999999&sort=desc&apikey=22XIHUPENNRKC33IRJIIAVBETPK1VS1MRN";
    @Autowired
    private EthTransferHistoryRepository ethTransferHistoryRepository;

    @Autowired
    private EtherscanAddressRepository addressRepository;

    @Scheduled(cron = "*/5 * * * * ? ")
    public void get() {
        List<EtherscanAddress> allAddress = addressRepository.findAll();
        allAddress.stream().forEach(address -> {
            logger.info("访问API 获取:address:" + address.getAddress() + "以太转账记录开始！");
            EthTransferHistory ethTransferHistory = ethTransferHistoryRepository.findLeastEthHistory(address.getId());
            HttpResponse rep = null;
            try {
                rep = HttpUtil.get(String.format(api, address.getAddress(), ethTransferHistory.getBlockNumber()), 3);
                if (rep == null || rep.getStatusLine().getStatusCode() != 200) {
                    return;
                } else {
                    String str = EntityUtils.toString(rep.getEntity());
                    JSONArray jsonArray = JSONObject.fromObject(str).getJSONArray("result");
                    String message = JSONObject.fromObject(str).getString("message");
                    List<Txlist> txlists = (List<Txlist>) jsonArray.toCollection(jsonArray, Txlist.class);
                    if (CollectionUtils.isEmpty(txlists)) {
                        logger.info("获取address出现" + message);
                        return;
                    }
                    List<EthTransferHistory> all = new ArrayList<>();
                    for (Txlist txlist : txlists) {
                        if (txlist.getValue().equals("0")) {
                            continue;
                        }
                        if (txlist.getHash().equals(ethTransferHistory.getTxHash())) {
                            break;
                        }
                        EthTransferHistory history = new EthTransferHistory();
                        history.setAddressId(address.getId());
                        history.setBlockNumber(txlist.getBlockNumber());
                        history.setTxHash(txlist.getHash());
                        if (txlist.getFrom().equals(address.getAddress())) {
                            history.setType(2);
                        } else {
                            history.setType(1);
                        }
                        history.setCount(new BigDecimal(txlist.getValue()).divide(new BigDecimal("1000000000000000000")));
                        history.setCreateAt(parseByTimeStamp(txlist.getTimeStamp()+"000"));
                        all.add(history);
                    }
                    if (!CollectionUtils.isEmpty(all)) {
                        ethTransferHistoryRepository.saveAll(all);
                    }
                }
                logger.info("访问API 获取:address:" + address.getAddress() + "以太转账记录完成！");
            } catch (IOException e) {
                logger.error("获取adddress:" + address.getAddress() + "失败！",e);
            } catch (ParseException e) {
                logger.error("时间转换错误",e);            }
        });
        logger.info("获取所有地址ETH 转账记录完成！");
    }

}
