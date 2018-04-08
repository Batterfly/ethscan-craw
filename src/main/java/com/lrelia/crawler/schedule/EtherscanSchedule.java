package com.lrelia.crawler.schedule;

import com.lrelia.crawler.entity.EtherscanAddress;
import com.lrelia.crawler.entity.TokenTransferHistory;
import com.lrelia.crawler.repository.EtherscanAddressRepository;
import com.lrelia.crawler.repository.TokenTransferHistoryRepository;
import com.lrelia.crawler.utils.CrawLerUtil;
import com.lrelia.crawler.utils.DateUtil;
import com.lrelia.crawler.utils.TxCache;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.expression.Lists;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        EtherscanAddress etherscanAddress = new EtherscanAddress();
        String address = "0x3f5ce5fbfe3e9af3971dd833d26ba9b5c936f0be";
        EtherscanAddress address1 = addressRepository.findByAddress(address);
        Connection connet = getConnection(address);
        try {
            Document document = connet.get();
            Elements elements = document.getElementsByTag("tbody").get(0).getElementsByTag("tr");
            elements.remove(0);

            List<TokenTransferHistory> token_history = new ArrayList<>();
            String firstTx = "";
            for (int i = 0; i < elements.size(); i++) {
                Elements temp = elements.get(i).children();
                String tx = temp.get(0).text();
                if (i == 0) {
                    firstTx = tx;
                }
                //cache
                if (!StringUtils.isEmpty(TxCache.getCache(address))) {
                    if (tx.equals(TxCache.getCache(address))) {
                        break;
                    }
                }
                TokenTransferHistory tokenTransferHistory = new TokenTransferHistory();
                String time = temp.get(1).children().attr("title");
                int type = temp.get(3).children().text().equals("OUT") ? 2 : 1;
                BigDecimal val = new BigDecimal(temp.get(5).text().replace(",", ""));
                String symbol = temp.get(6).text();
                tokenTransferHistory.setSymbol(symbol);
                tokenTransferHistory.setAddressId(etherscanAddress.getId());
                tokenTransferHistory.setCount(val);
                tokenTransferHistory.setAddressId(address1.getId());
                try {
                    tokenTransferHistory.setCreateAt(DateUtil.parse(time));
                } catch (ParseException e) {
                    System.out.println("bubgubgugbu");
                    System.out.println(e.getMessage());
                }
                tokenTransferHistory.setType(type);

                token_history.add(tokenTransferHistory);
            }
            TxCache.setCache(address, firstTx);
            if (!CollectionUtils.isEmpty(token_history)) {
                tokenTransferHistoryRepository.saveAll(token_history);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    public static Connection getConnection(String address) {
        Connection connection = Jsoup.connect("https://etherscan.io/address-tokenpage?a=" + address);
        connection.header("authority", "etherscan.io");
        connection.header("method", "GET");
        connection.header("path", "/address-tokenpage?a=" + address);
        connection.header("scheme", "https");
        connection.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connection.header("cookie", "__cfduid=d91b76602240aa86cb8847f387b7a452e1521132371; _ga=GA1.2.1779016044.1521132377; ASP.NET_SessionId=h0n115ookwrezfb0ujtwwtff; __cflb=767839123; _gid=GA1.2.444928896.1522677744");
        connection.header("upgrade-insecure-requests", "1");
        connection.header("User-Agent", CrawLerUtil.getRandomUA());
        connection.timeout(50000);
        return connection;
    }
}
