package com.cloudhua.imageplatform.service.utils;

import com.alibaba.fastjson.JSONObject;
import com.cloudhua.imageplatform.service.log.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by yangchao on 2017/7/18.
 * 简单http post get请求工具
 * 静态工具类
 */
public class HttpUtils {

    private static Logger logger = Logger.getInst(HttpUtils.class);//日志

    private static RequestConfig requestConfig = RequestConfig.custom()//Request配置
            .setSocketTimeout(15000)//设置超时时间
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    public static byte[] getData(String downloadurl) {//url获取数据
        logger.debug("getData method downloadurl:" + downloadurl);//日志
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {//客户端设置请求配置
            HttpGet httpget = new HttpGet(downloadurl);//url新建httpGET
            HttpResponse httpResponse = httpClient.execute(httpget);//执行GET得到Response
            try (InputStream is = httpResponse.getEntity().getContent()) {//return Response content
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {//新建字节数组输出管道
                    byte[] buffer = new byte[1024];//字节数组的缓冲区
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {//将输入流中的数据传向字节数组
                        baos.write(buffer, 0, len);//字节数组中的值写向输出管道
                    }
                    logger.debug("getData method return.");
                    return baos.toByteArray();//输出管道转化为字节数组
                }
            }
        } catch (IOException e) {//否则捕获异常，利用logger.error
            logger.error("HttpUtils get file content data error. downloadUrl:" + downloadurl, e);
        }
        logger.debug("getData method return has something error.");
        return null;
    }

    /**
     * 请求xserver
     *
     * @param url        网盘url地址
     * @param bodyParams 请求参数<String,Object>
     * @return 返回网盘响应信息，返回码200返回body数据，其他返回码返回null表示请求出错
     */
    public static String postJsonData(String url, Map<String, Object> bodyParams) {
        logger.debug("postXserver method url:" + url + " bodyParams:" + bodyParams);
        return postJsonData(url, bodyParams, null);
    }

    /**
     * 请求xserver
     *
     * @param url        网盘url地址
     * @param bodyParams 请求参数
     * @param headers    请求头信息
     * @return 返回网盘响应信息，返回码200返回body数据，其他返回码返回null表示请求出错
     */
    public static String postJsonData(String url, Map<String, Object> bodyParams, Map<String, String> headers) {//POST
        logger.debug("postXserver method url:" + url + " bodyParams:" + bodyParams + " headers:" + headers);
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {//建立客户端连接
            HttpPost httpPost = new HttpPost(url);//url新建POST
            if (headers != null) {//如果有头信息
                for (String key : headers.keySet()
                        ) {
                    String value = headers.get(key);
                    httpPost.addHeader(key, value);//在POST中加入头信息
                }
            }

            if (bodyParams != null) {//如果有请求参数
                JSONObject reqJson = new JSONObject(bodyParams);//把参数体<String,Object>新建成JSON对象
                StringEntity entity = new StringEntity(reqJson.toString());//又把JSON对象转化成字符串实体
                httpPost.setEntity(entity);//把字符串实体加入到POST中
            }


            HttpResponse httpResponse = httpClient.execute(httpPost);//Client执行httpPOST，得到Response
            if (httpResponse.getStatusLine().getStatusCode() != 200) {//Response的状态码不是200，就返回
                return null;
            } else {//	200：请求已成功，请求所希望的响应头或数据体将随此响应返回。
                String responseStr = EntityUtils.toString(httpResponse.getEntity());//响应转成字符串
                logger.debug("postXserver method return responseStr:" + responseStr);
                return responseStr;//返回字符串
            }
        } catch (IOException e) {
            logger.error("HttpUtils get file content data error. url:" + url + " bodyParams:" + bodyParams + " headers:" + headers, e);
        }
        logger.debug("postXserver method return has something error.");
        return null;
    }

    public static String get(String url) {//url来得到响应信息
        logger.debug("get method url:" + url);
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {//建立客户机的http连接
            HttpGet httpGet = new HttpGet(url);//url新建httpGET
            HttpResponse httpResponse = httpClient.execute(httpGet);//客户机执行GET得到响应
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                return null;
            } else {//如果响应状态码是200，那么就返回响应字符串格式
                String responseStr = EntityUtils.toString(httpResponse.getEntity());
                logger.debug("get method return responseStr:" + responseStr);
                return responseStr;
            }
        } catch (IOException e) {
            logger.error("HttpUtils get method error. url:" + url, e);
        }
        logger.debug("get method return has something error.");
        return null;
    }
}
