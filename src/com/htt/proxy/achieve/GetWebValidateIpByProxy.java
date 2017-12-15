package com.htt.proxy.achieve;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;

import com.htt.proxy.util.HttpClientUtil;

/**
  * @ClassName: GetWebValidateIpByProxy
  * @Description: 验证已下载到本地的代理ip集合是否有效
  * @author ex-hutao001
  * @date 2017年9月30日 下午2:43:53
  */
public class GetWebValidateIpByProxy
{
    static String resultfilePath = "D://data/paic/proxyip/ip_validate.txt";
    static File resultfile = new File(resultfilePath);
    static List<String> listResult = new ArrayList<String>();
    
    public static void getServices(String host, Integer port)
    {
        try
        {
            HttpClient httpClient = HttpClientUtil.getHttpClient(host, port);
            GetMethod get = null;
            get = new GetMethod(
                    "https://www.baidu.com/");  // 再次请求百度首页 
            long begeinTime = System.currentTimeMillis(); // 请求开始时间
            int statuCode = httpClient.executeMethod(get);
            long endTime = System.currentTimeMillis();    // 请求结束时间
            long time = endTime - begeinTime;   // 获得请求反应时间
            String str = get.getResponseBodyAsString();
            if (str != null && !str.equals(""))
            {
                if (str.contains("www.baidu.com"))  // 如果含有baidu，证明获取成功，代理IP有效
                {
                    listResult.add(host + ":" + port);
                    FileUtils.writeStringToFile(resultfile, host + ":" + port + "\n", true);
                    System.out.println("请求返回code：" + statuCode + ";请求返回结果：" + str + ";反应时间为：[" + time/1000 + "秒]");
                }
            }
        } catch (HttpException e)
        {
            System.out.println("[" + host + ":" + port + "] 已失效");
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println("文件有误，请修改文件路径");
            e.printStackTrace();
        }
    }
    
    /**
     * @Title: getProxyIpToFile 
     * @Description: 把获取到的有效的代理IP地址重写写入另一个文件
     * @param filepath void
     * @author ex-hutao001
     * @date 2017年9月30日
     */
    public static void getProxyIpToFile(String filepath)
    {
        File file = new File(filepath);
        try
        {
            List<String> listIp = FileUtils.readLines(file);
            for (String str_ip : listIp)
            {
                String[] sip = str_ip.split(":");
                getServices(sip[0].toString(), Integer.parseInt(sip[1]));
            }
            System.out.println("参与验证的代理IP共：" + listIp.size());
//            FileUtils.writeLines(resultfile, listResult);
            System.out.println("验证通过的IP有：" + listResult.size());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    // 主方法入口
    public static void main(String[] args)
    {
        getProxyIpToFile("D://data/paic/proxyip/ip1.txt");
    }
}
