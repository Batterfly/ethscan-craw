package com.lrelia.crawler.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/5
 */
public class LogUtil {
    /**
     * 成功状态
     */
    public static final Integer OK = 0;

    /**
     * 系统错误
     */
    public static final Integer SYS_ERROR = -1;

    /**
     * 系统警告
     */
    public static final Integer SYS_WARN = -2;

    /**
     * 日志格式
     */
    private final String LOG_FORMAT = "%d | %s";

    private Logger log = null;

    private LogUtil(Class<?> clazz) {
        log = LoggerFactory.getLogger(clazz);
    }

    public static LogUtil newInstance(Class<?> clazz) {
        return new LogUtil(clazz);
    }

    /**
     * 打印警告日志
     *
     * @param message
     */
    public void warn(String message) {
        log.warn(String.format(LOG_FORMAT, SYS_WARN, message));
    }

    /**
     * 打印警告日志
     *
     * @param code
     * @param message
     */
    public void warn(Integer code, String message) {
        log.warn(String.format(LOG_FORMAT, code, message));
    }

    /**
     * 打印info日志
     *
     * @param message
     */
    public void info(String message) {
        log.info(String.format(LOG_FORMAT, OK, message));
    }

    /**
     * 打印info日志
     *
     * @param code
     * @param message
     */
    public void info(Integer code, String message) {
        log.info(String.format(LOG_FORMAT, code, message));
    }


    /**
     * 打印debug日志
     *
     * @param message
     */
    public void debug(String message) {
        log.debug(String.format(LOG_FORMAT, OK, message));
    }

    /**
     * 打印debug日志
     *
     * @param code
     * @param message
     */
    public void debug(Integer code, String message) {
        log.debug(String.format(LOG_FORMAT, code, message));
    }


    /**
     * 打印debug日志
     *
     * @param message
     */
    public void error(String message, Exception e) {
        log.error(String.format(LOG_FORMAT, SYS_ERROR, message), e);
    }

    /**
     * 打印debug日志
     *
     * @param code
     * @param message
     */
    public void error(Integer code, String message, Exception e) {
        log.error(String.format(LOG_FORMAT, code, message), e);
    }

}