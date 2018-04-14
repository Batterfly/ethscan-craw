package com.lrelia.crawler.controller;

import com.lrelia.crawler.model.EchartSeries;
import com.lrelia.crawler.repository.TokenTransferHistoryRepository;
import com.lrelia.crawler.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import net.sf.json.JSONObject;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/10
 */
@RestController
public class EchartController {

    @Autowired
    TokenTransferHistoryRepository historyRepository;

    @GetMapping(value = "chart")
    public String getchart(@RequestParam("address_id") long addressId, @RequestParam("symbol") String symbol) throws ParseException {

        List<String> xAxisData = DateUtil.getPastDateArray(6);

        Map<String, BigDecimal> inMap = new HashMap<>();
        Map<String, BigDecimal> outMap = new HashMap<>();

        List<Object[]> inCalate = historyRepository.calculateAltTokenSumByCondition(addressId, 1, symbol,
                DateUtil.parseToDateTime(xAxisData.get(0)+" 00:00:00"), DateUtil.parseToDateTime(xAxisData.get(6)+" 23:59:59"));
        List<Object[]> outCalate = historyRepository.calculateAltTokenSumByCondition(addressId, 2, symbol,
                DateUtil.parseToDateTime(xAxisData.get(0)+" 00:00:00"), DateUtil.parseToDateTime(xAxisData.get(6)+" 23:59:59"));
        inCalate.stream().forEach(obj -> inMap.put(obj[1].toString(), new BigDecimal(obj[0].toString())));
        outCalate.stream().forEach(obj -> outMap.put(obj[1].toString(), new BigDecimal(obj[0].toString())));

        LinkedList<BigDecimal> insum = new LinkedList<>();
        LinkedList<BigDecimal> outsum = new LinkedList<>();
        String year = DateUtil.getCurrentYear() + "-";
        xAxisData.stream().forEach(date -> insum.add(inMap.get(year + date) == null ? BigDecimal.ZERO : inMap.get(year + date)));
        xAxisData.stream().forEach(date -> outsum.add(outMap.get(year + date) == null ? BigDecimal.ZERO : outMap.get(year + date)));

        List<JSONObject> seriesList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            EchartSeries series = null;
            if (i == 0) {
                series = new EchartSeries("转入", EchartSeries.TYPE_LINE, insum);
            } else {
                series = new EchartSeries("转出", EchartSeries.TYPE_LINE, outsum);
            }
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("name", series.getName());
            jsonObject2.put("type", "bar");
            jsonObject2.put("data", series.getData());
            seriesList.add(jsonObject2);
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("xAxisData", xAxisData);
        jsonObject1.put("seriesList", seriesList);

        return jsonObject1.toString();
    }

    public static void main(String[] args) throws ParseException {
        List<String> xAxisData = DateUtil.getPastDateArray(6);
        System.out.println(DateUtil.parseToDateTime(xAxisData.get(6)+" 23:59:59"));

    }
}
