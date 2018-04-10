package com.lrelia.crawler.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/10
 */
public class EchartSeries {

    public static String TYPE_LINE = "line";
    public static String TYPE_BAR = "bar";

    public String name;
    public String type;
    public LinkedList<BigDecimal> data;

    public EchartSeries(String name, String type, LinkedList<BigDecimal> data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public static String getTypeLine() {
        return TYPE_LINE;
    }

    public static void setTypeLine(String typeLine) {
        TYPE_LINE = typeLine;
    }

    public static String getTypeBar() {
        return TYPE_BAR;
    }

    public static void setTypeBar(String typeBar) {
        TYPE_BAR = typeBar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinkedList<BigDecimal> getData() {
        return data;
    }

    public void setData(LinkedList<BigDecimal> data) {
        this.data = data;
    }
}
