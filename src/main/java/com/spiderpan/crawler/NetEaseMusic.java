package com.spiderpan.crawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    public static void ShareSongStart(String path, String url) throws IOException {

        Pattern p=Pattern.compile("id=(.*?)&userid");
        Matcher m=p.matcher(url);
        m.find();//这个必须加，否则空指针
        String id = m.group(1);
        System.out.println(id);
        String downloadUrl="http://music.163.com/song/media/outer/url?id="+ id + ".mp3";
        SpiderUtil.commenDownload(path + id + ".mp3",downloadUrl);

    }

    public static void SearchStart(String path,String keyWord){

    }


}

