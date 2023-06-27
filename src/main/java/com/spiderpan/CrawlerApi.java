package com.spiderpan;

import com.spiderpan.crawler.BianWallpaper;
import com.spiderpan.crawler.Bilibili.BiliVideo;
import com.spiderpan.crawler.FalooNovel;
import com.spiderpan.crawler.NetEaseMusic;

import java.io.IOException;

public class CrawlerApi {

    public static void main(String[] args) throws IOException {
//        Bilibili("BV1Kk4y1t73M","D:/StudySubjects/Javaweb/Bilibili/");
//        NetEaseTopSong("D:/StudySubjects/Javaweb/music/");
//        Wallpaper("D:/StudySubjects/Javaweb/WallPaper/","dongman",2);
        Faloo("D:/StudySubjects/Javaweb/Novel/","1321911",40);
    }

    //飞卢小说：输入路径、小说编号和要下载的章数
    public static void Faloo(String path, String url, int pagenum){
        FalooNovel.start(path,url,pagenum);
    }

    //B站视频：输入bv号，和路径(如D:/StudySubjects/Javaweb/Bilibili/)
    public static void Bilibili(String bv,String path){
        BiliVideo.start(bv,path);
    }

    //网易云热歌榜：输入路径(如D:/StudySubjects/Javaweb/Music/)
    public static void NetEaseTopSong(String path) throws IOException {
        NetEaseMusic.TopStart(path);
    }
    //彼岸壁纸：输入路径和壁纸种类，可以设置爬取页面数，一个页面约十几张图
    public static void Wallpaper(String path,String cata,int pageNum){
        BianWallpaper.start(path,cata,pageNum);
    }

    //未完成，禁用
    public static void NetEaseSearchSong(String songName,String path) throws IOException {
        NetEaseMusic.SearchStart(songName,path);
    }
}
