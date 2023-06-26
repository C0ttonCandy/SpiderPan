package com.spiderpan;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class TestBili {
    public static void main(String[] args) throws IOException {
        String header = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.51";

        Document doc = Jsoup.connect(" https://cn-hbwh-fx-01-08.bilivideo.com/upgcxcode/03/60/774066003/774066003_nb3-1-30032.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1687596438&gen=playurlv2&os=bcache&oi=976231297&trid=00005d479ffaa246488ba5b9d6a427aaa5d5u&mid=0&platform=pc&upsig=e03affeae06583854d34c1f33b312f7f&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform&cdnid=3878&bvc=vod&nettype=0&orderid=0,3&buvid=8C370753-C04C-8B61-B1F8-2E95B7862A3238188infoc&build=0&agrr=1&bw=32341&np=151388311&logo=80000000")
                .userAgent(header)
                .referrer("https://www.bilibili.com/")
                .timeout(8000)
                .get();
        //被淘汰的匹配方法：dom方法
        //Elements elements = doc.getElementsByTag("ul");
        //Elements content = elements.get(2).getElementsByAttribute("href");

        //匹配，使用CSS匹配器



        //http://music.163.com/song/media/outer/url?id=534544522.mp3
        //下载接口


    }
}
