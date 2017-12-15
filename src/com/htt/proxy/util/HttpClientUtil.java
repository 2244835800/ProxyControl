package com.htt.proxy.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;

/**
 * @ClassName: HttpClientUtil
 * @Description: 数据抓取抽象类
 * @author ex-hutao001
 * @date 2017年9月29日 16:33:45
 *
 */
public abstract class HttpClientUtil
{
    private static HttpClient httpClient = null;
    /** 超时时间 */
    private static int timeout = 50000;

    /**
     * @author:ex-hutao001
     * @MethodName: 无参构造器
     * @Date: 2017年9月29日 16:37:45
     * @Description: TODO
     */
    public HttpClientUtil()
    {
    }

    /**
     * @author:ex-hutao001
     * @MethodName: getHttpClient
     * @Date: 2017年9月29日 16:38:42
     * @Description: 获取httpClient实例的方法
     * @return HttpClient
     */
    public static HttpClient getHttpClient()
    {
        httpClient = new HttpClient(new SimpleHttpConnectionManager(true));
        httpClient.getParams().setSoTimeout(timeout);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        return httpClient;
    }
    
    /**
     * @author:ex-hutao001
     * @MethodName: getHttpClient(String host, Integer port)
     * @Date: 2017年9月29日 16:40:21
     * @Description: 根据代理IP和端口号 获取httpClient实例的方法
     * @return HttpClient
     */
    public static HttpClient getHttpClient(String host, Integer port)
    {
        httpClient = new HttpClient(new SimpleHttpConnectionManager(true));
        httpClient.getParams().setSoTimeout(timeout);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        httpClient.getHostConfiguration().setProxy(host, port);
        return httpClient;
    }
}
