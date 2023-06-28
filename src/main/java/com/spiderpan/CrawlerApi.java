package com.spiderpan;

import com.spiderpan.crawler.BianWallpaper;
import com.spiderpan.crawler.Bilibili.BiliVideo;
import com.spiderpan.crawler.FalooNovel;
import com.spiderpan.crawler.NetEaseMusic;

import java.io.IOException;

public class CrawlerApi {

    public static void main(String[] args) throws IOException {
        //可参考以下函数输入参数

//        Bilibili("BV1Kk4y1t73M","D:/StudySubjects/Javaweb/Bilibili/");
//        NetEaseTopSong("D:/StudySubjects/Javaweb/music/");
//        Wallpaper("D:/StudySubjects/Javaweb/WallPaper/","dongman",2);
        Faloo("D:/StudySubjects/Javaweb/CrawlerContent/Novel/","1321911",30,40);
//        NetEaseSearchSong("D:/StudySubjects/Javaweb/music/","https://y.music.163.com/m/song?id=1471055851&userid=134291887&dlt=0846");
    }

    //网易云热歌榜：输入路径(如D:/StudySubjects/Javaweb/Music/)
    public static void NetEaseTopSong(String path) throws IOException {
        NetEaseMusic.TopStart(path);
    }

    //输入保存路径和歌曲分享链接
    public static void NetEaseSearchSong(String path,String url) throws IOException {
        NetEaseMusic.ShareSongStart(path,url);
    }

    //飞卢小说：输入路径、小说编号和要下载的章数
    public static void Faloo(String path, String url,int start ,int finish){
        FalooNovel.start(path,url,start,finish);
    }

    //B站视频：输入bv号，和路径(如D:/StudySubjects/Javaweb/Bilibili/)
    public static void Bilibili(String bv,String path){
        BiliVideo.start(bv,path);
    }


    //彼岸壁纸：输入路径和壁纸种类，可以设置爬取页面数，一个页面约十几张图
    public static void Wallpaper(String path,String cata,int pageNum){
        BianWallpaper.start(path,cata,pageNum);
    }


}
