package com.lrelia.crawler.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.Map.Entry;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/5
 */
public class HttpUtil{

    private static final LogUtil logger = LogUtil.newInstance(HttpUtil.class);

    public static final String DEFAULT_CHARSET = "utf-8";

    public static HttpResponse get(String url, int retry, Map<String, String> headers) throws IOException {
        return get(url, retry, headers, 15000);
    }

    /**
     * get请求
     *
     * @param url     请求连接地址
     * @param retry   重试次数
     * @param headers 请求头参数
     * @return
     * @throws IOException
     */
    public static HttpResponse get(String url, int retry, Map<String, String> headers, int socketTimeout) throws IOException {
        int tryNum = 0;
        HttpResponse httpResponse = null;
        IOException exception = null;
        Request request = Request.Get(url).connectTimeout(15000).socketTimeout(socketTimeout);
        if (null != headers) {
            for (Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }

        while (tryNum < retry) {
            tryNum++;
            try {
                httpResponse = request.execute().returnResponse();
            } catch (IOException e) {
                exception = e;
            }

            if (null != httpResponse) {
                break;
            }

        }
        logger.debug(String.format("尝试了 %d 次请求 %s", tryNum, url));
        if (null == httpResponse) {
            throw exception;
        } else {
            return httpResponse;
        }
    }

    /**
     * get请求
     *
     * @param url   请求连接地址
     * @param retry 重试次数
     * @return
     * @throws IOException
     */
    public static HttpResponse get(String url, int retry) throws IOException {
        return get(url, retry, null);
    }

    /**
     * get请求
     *
     * @param url   请求连接地址
     * @param retry 重试次数
     * @return
     * @throws IOException
     */
    public static String getString(String url, int retry) throws IOException {
        HttpResponse response = get(url, retry, null);
        return IOUtils.toString(response.getEntity().getContent(), DEFAULT_CHARSET);
    }

    /**
     * get请求
     *
     * @param url     请求连接地址
     * @param retry   重试次数
     * @param headers 请求头参数
     * @return
     * @throws IOException
     */
    public static String getString(String url, int retry, Map<String, String> headers, int socketTimeout) throws IOException {
        HttpResponse response = get(url, retry, headers, socketTimeout);
        return IOUtils.toString(response.getEntity().getContent(), DEFAULT_CHARSET);
    }

    /**
     * get请求
     *
     * @param url     请求连接地址
     * @param retry   重试次数
     * @param headers 请求头参数
     * @return
     * @throws IOException
     */
    public static String getString(String url, int retry, Map<String, String> headers) throws IOException {
        HttpResponse response = get(url, retry, headers);
        return IOUtils.toString(response.getEntity().getContent(), DEFAULT_CHARSET);
    }

    /**
     * post请求
     *
     * @param url
     * @param retry
     * @param params  post请求参数
     * @param headers post请求头
     * @return
     * @throws IOException
     */
    public static HttpResponse post(String url, int retry, Map<String, Object> params, Map<String, String> headers) throws IOException {
        int tryNum = 0;
        IOException exception = null;
        HttpResponse httpResponse = null;

        Request request = Request.Post(url).connectTimeout(15000).socketTimeout(15000);
        if (null != headers) {
            for (Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if (null != params) {
            Form form = Form.form();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                form.add(entry.getKey(), null != entry.getValue() ? entry.getValue().toString() : "");
            }
            request.bodyForm(form.build());
        }

        while (tryNum < retry) {
            tryNum++;
            try {
                httpResponse = request.execute().returnResponse();
            } catch (IOException e) {
                exception = e;
            }

            if (null != httpResponse) {
                break;
            }
        }

        logger.debug(String.format("尝试了 %d 次请求 %s", tryNum, url));
        if (null == httpResponse) {
            throw exception;
        } else {
            return httpResponse;
        }
    }

    /**
     * post请求
     *
     * @param url
     * @param retry
     * @param headers post请求头
     * @param params  post请求参数
     * @return
     * @throws IOException
     */
    public static String postString(String url, int retry, Map<String, Object> params, Map<String, String> headers) throws IOException {
        HttpResponse response = post(url, retry, params, headers);
        return IOUtils.toString(response.getEntity().getContent(), DEFAULT_CHARSET);
    }

    /**
     * post请求
     *
     * @param url
     * @param retry
     * @param params post请求参数
     * @return
     * @throws IOException
     */
    public static HttpResponse post(String url, int retry, Map<String, Object> params) throws IOException {
        return post(url, retry, params, null);
    }

    /**
     * post请求
     *
     * @param url
     * @param retry
     * @param params post请求参数
     * @return
     * @throws IOException
     */
    public static String postString(String url, int retry, Map<String, Object> params) throws IOException {
        HttpResponse response = post(url, retry, params, null);
        return IOUtils.toString(response.getEntity().getContent(), DEFAULT_CHARSET);
    }

    /**
     * post请求
     *
     * @param url
     * @param retry
     * @param headers post请求头
     * @return
     * @throws IOException
     */
    public static HttpResponse post(String url, Map<String, String> headers, int retry) throws IOException {
        return post(url, retry, null, headers);
    }

    /**
     * post请求
     *
     * @param url
     * @param retry
     * @param headers post请求头
     * @return
     * @throws IOException
     */
    public static String postString(String url, Map<String, String> headers, int retry) throws IOException {
        HttpResponse response = post(url, retry, null, headers);
        return IOUtils.toString(response.getEntity().getContent(), DEFAULT_CHARSET);
    }

    /**
     * post请求
     *
     * @param url
     * @param retry
     * @return
     * @throws IOException
     */
    public static HttpResponse post(String url, int retry) throws IOException {
        return post(url, retry, null, null);
    }

    /**
     * post请求
     *
     * @param url
     * @param retry
     * @return
     * @throws IOException
     */
    public static String postString(String url, int retry) throws IOException {
        HttpResponse response = post(url, retry, null, null);
        return IOUtils.toString(response.getEntity().getContent(),DEFAULT_CHARSET);
    }


    /**
     * URL 转码
     *
     * @param str
     * @param charset
     * @return
     */
    public static String getURLEncoderString(String str, String charset) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = URLEncoder.encode(str, charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("编码失败", e);
        }
        return result;
    }

    /**
     * URL 解码
     *
     * @param str
     * @param charset
     * @return
     */
    public static String getURLDecoderString(String str, String charset) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = URLDecoder.decode(str, charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("解码失败", e);
        }
        return result;
    }

    /**
     * postjson请求，设置超时时间
     *
     *
     * @param url
     * @param jsonParams
     * @param headerMap
     * @return
     * @throws IOException
     */
    public static String postForJson(String url, String jsonParams, Map<String, String> headerMap) throws IOException {
        return postForJson(url, jsonParams, headerMap, 15000);
    }
    /**
     * postjson请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String postForJson(String url, String jsonParams, Map<String, String> headerMap, int socketTimeout) throws IOException {
        String body = "";
        int tryNum = 0;
        int retry = 3;
        CloseableHttpResponse response = null;
        Map<String, String> map = new HashMap();
        map.put("Content-type", "application/json;charset=UTF-8");

        if (null != headerMap && !headerMap.isEmpty()) {
            map.putAll(headerMap);
        }

        //创建httpclient对象
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(15000).setSocketTimeout(socketTimeout).build();
            httpPost.setConfig(requestConfig);

            //配置参数
            StringEntity stringEntity = new StringEntity(jsonParams, DEFAULT_CHARSET);
            httpPost.setEntity(stringEntity);

            //设置header信息
            //指定报文头【Content-type】、【User-Agent】

            for (String key : map.keySet()) {
                httpPost.setHeader(key, map.get(key));
            }

            //执行请求操作，并拿到结果（同步阻塞）
            while (tryNum < retry) {
                tryNum++;
                response = client.execute(httpPost);
                if (null != response) {
                    break;
                }
            }

            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {

                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, DEFAULT_CHARSET);
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
        } catch (IOException e) {
            throw e;
        }
        return body;
    }

}