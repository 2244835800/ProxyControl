package com.htt.proxy.util;

import org.jsoup.Jsoup;


/**
  * @ClassName: ProxyUtil
  * @Description: 代理IP工具 ping百度首页
  * @author ex-hutao001
  * @date 2017年9月29日 16:45:56
  */
public class ProxyUtil
{
    /**
     * @Title: checkProxy 
     * @Description: 对请求过来的ip地址进行一次过滤
     * @param ip 代理IP
     * @param port 端口号
     * @return boolean 
     * @author ex-hutao001
     * @date 2017年9月30日
     */
    public static boolean checkProxy(String ip, Integer port)
    {
        try {
            Jsoup.connect("http://www.baidu.com")
            .timeout(3*1000)
            .proxy(ip, port, null)
            .get();
            System.out.println("[" + ip + ":" + port + "] 有效ip...");
            return true;
        } catch (Exception e) {
            System.out.println("[" + ip + ":" + port + "] 是一个无效ip");
            return false;
        }
    }
}
