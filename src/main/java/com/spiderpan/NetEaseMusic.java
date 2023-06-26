package com.spiderpan;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;


import java.io.*;




public class NetEaseMusic {



    public static void TopStart(String filePath) throws IOException {

        String header = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.51";

        Document doc = Jsoup.connect("https://music.163.com/discover/toplist?id=3778678")
                .userAgent(header)
                .timeout(8000)
                .get();

        Elements content1= doc.select("ul.f-hide li a");

        //http://music.163.com/song/media/outer/url?id=534544522.mp3
        //下载接口

        String id; String name;

        System.out.println(content1);

        for (Element e: content1
             ) {
            id = e.attr("href").substring(9);
            name = e.html();
            String path = filePath + name+".mp3";

            String url="http://music.163.com/song/media/outer/url?id="+id+ ".mp3";
            SpiderUtil.commenDownload(path,url);
        }
    }
    public static void SearchStart(String name,String p) throws IOException {
        //https://music.163.com/weapi/cloudsearch/get/web?csrf_token=
        //https://music.163.com/#/search/m/?s="+name+"&type=1
        String searchUrl = "https://i.y.qq.com/n2/m/share/details/taoge.html?id=8660417223&hosteuin=";
        String header = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.51";

        Document doc = Jsoup.connect(searchUrl)
                .userAgent(header)
                .timeout(8000)
                .get();
        System.out.println(doc);

    }
}

