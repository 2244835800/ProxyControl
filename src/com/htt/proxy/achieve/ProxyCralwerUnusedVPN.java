package com.htt.proxy.achieve;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.htt.proxy.util.ProxyUtil;

/**
  * @ClassName: ProxyCralwerUnusedVPN
  * @Description: 我们只要有效的代理IP kuaidaili有效IP数量并不多，也可以对代码进行调整 找其它网站的
  * @author ex-hutao001
  * @date 2017年9月29日 16:55:35
  */
public class ProxyCralwerUnusedVPN
{
    // 存放从网站上下载下来的 并且有效的ip地址与端口
    static String filePath = "D://data/paic/proxyip/ip201702.txt";

    public void startCrawler(){  
        dailiCom("http://www.kuaidaili.com/ops/proxylist/", 10);  // 此处建议写15页
    }  
    
    /**
     * @Title: dailiCom 
     * @Description: 根据代理网站的请求地址 和页码数量请求“有效的代理Ip”
     * @param baseUrl 代理网站url地址
     * @param totalPage 页码
     * @author ex-hutao001
     * @date 2017年9月30日
     */
    private void dailiCom(String baseUrl,int totalPage){
 
        String ipReg = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3} \\d{1,6}"; 
        Pattern ipPtn = Pattern.compile(ipReg); 
        
        File file = new File(filePath);
        
        /**
         * 请求 并解析
         */
        for (int i = 1; i <= totalPage; i++) {  
            try { 
                // 以下请求参数 请通过抓包实时获取
                Document doc = Jsoup.connect(baseUrl) 
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")  
                        .header("Accept-Encoding", "gzip, deflate")  
                        .header("Accept-Language", "zh-CN,zh;q=0.9") 
                        .header("Cookie", "yd_cookie=9efa1c9e-d676-4165f2a7cf8b0090dfbece54ebabdef86262; _ydclearance=6a15ba0462a8d7c41d70061b-3fc4-4905-ba94-0c83dd86cd52-1513352867; channelid=0; sid=1513347445316958; _ga=GA1.2.445299873.1513338268; _gid=GA1.2.553565736.1513338268; _gat=1; Hm_lvt_7ed65b1cc4b810e9fd37959c9bb51b31=1513338268,1513340370,1513346467; Hm_lpvt_7ed65b1cc4b810e9fd37959c9bb51b31=1513347474")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")  
                        .timeout(30*1000)  
                        .get();  
                System.out.println(doc.text());  // 打印请求页面内容
                
                Matcher m = ipPtn.matcher(doc.text()); 
                
                while (m.find())
                {
                    String[] strs = m.group().split(" ");  
                    if(ProxyUtil.checkProxy(strs[0],Integer.parseInt(strs[1]))){  
                        System.out.println(strs[0].toString() + ":" + strs[1]);
                        FileUtils.writeStringToFile(file, strs[0].toString() + ":" + strs[1].toString() + "\r\n", true);
                    }
                }
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
    // 主方法入口
    public static void main(String[] args)
    {
        ProxyCralwerUnusedVPN proxyCrawler = new ProxyCralwerUnusedVPN();
        proxyCrawler.startCrawler();
    }
}
