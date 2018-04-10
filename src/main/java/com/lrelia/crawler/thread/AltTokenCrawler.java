package com.lrelia.crawler.thread;

import com.lrelia.crawler.async.AsyncSaveDbService;
import com.lrelia.crawler.entity.EtherscanAddress;
import com.lrelia.crawler.entity.TokenTransferHistory;
import com.lrelia.crawler.init.CoinMarketInit;
import com.lrelia.crawler.repository.EtherscanAddressRepository;
import com.lrelia.crawler.utils.CrawLerUtil;
import com.lrelia.crawler.utils.DateUtil;
import com.lrelia.crawler.utils.LogUtil;
import com.lrelia.crawler.utils.TxCache;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/10
 */
public class AltTokenCrawler implements Runnable {

    private static final LogUtil logger = LogUtil.newInstance(AltTokenCrawler.class);

    private EtherscanAddress address;

    private AsyncSaveDbService asyncSaveDbService;

    private Connection connection;

    public AltTokenCrawler(EtherscanAddress address, AsyncSaveDbService asyncSaveDbService) {
        this.address = address;
        this.asyncSaveDbService = asyncSaveDbService;
    }

    @Override
    public void run() {
        EtherscanAddress etherscanAddress = address;
        String address = etherscanAddress.getAddress();
        connection = getConnection(address);
        try {
            Document document = connection.get();
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
                try {
                    tokenTransferHistory.setCreateAt(DateUtil.parse(time));
                } catch (ParseException e) {
                    logger.error(e.getMessage(), e);
                }
                tokenTransferHistory.setType(type);

                token_history.add(tokenTransferHistory);
            }
            TxCache.setCache(address, firstTx);

            asyncSaveDbService.saveTokenHistorys(token_history);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
