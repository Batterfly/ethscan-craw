package com.lrelia.crawler.utils;

import java.text.DecimalFormat;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/9
 */
public class NumberUtil {

    public static String formatTosepara(float data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(data);
    }
    public static String formatToseparaRemainTwo(float data) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(data);
    }
}
