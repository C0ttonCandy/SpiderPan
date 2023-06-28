package com.spiderpan.crawler;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpiderUtil {
    //普通下载工具
    public static void commenDownload(String path, String u) throws IOException {
        File file = new File(path);
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();
        //  if 检测是否已存在文件，停止下载
        if (file.exists())
            return;

        URL url = new URL(u);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len;
        while ((len=inputStream.read(buffer))!=-1){
            fos.write(buffer,0,len);//写出这个数据
        }
        fos.close();
        inputStream.close();
        urlConnection.disconnect();//断开连接
    }
    //B站下载工具
    public static void fileDownload(String path, String u){
        Spider.create(new BiliDownloadProcessor())
                .addUrl(u)
                .addPipeline(new DownloadPipeline(path))
                .thread(10)
                .run();
    }
    //壁纸下载工具
    public static void WallPaperDownload(String path, String u){
        Spider.create(new WallPaperDownloadProcessor())
                .addUrl(u)
                .addPipeline(new DownloadPipeline(path))
                .thread(10)
                .run();
    }

    public static void mergeData(String namePath) throws IOException {
        System.out.println("文件正在生成中，请稍候···");
        String com = "ffmpeg -i " + namePath + ".mp4 -i " + namePath + ".mp3 -c:v copy -c:a aac -strict experimental " + namePath +"output.mp4";
        Process process = Runtime.getRuntime().exec(com);
        System.out.println("生成成功，记得查收！");
    }

    //根据字符数组下载
    public static void byteDownload(String path,byte[] msg,Boolean inOneFile){
        File file = new File(path);
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();


        //  if 检测是否已存在文件，停止下载
        if (file.exists() && !inOneFile)
            return;




        InputStream inputStream = new ByteArrayInputStream(msg);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file,true);
            byte[] buffer = new byte[1024];
            int len;
            while ((len=inputStream.read(buffer))!=-1){
                fos.write(buffer,0,len);//写出这个数据
            }
            fos.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class WallPaperDownloadProcessor implements PageProcessor{

    @Override
    public void process(Page page) {

        page.putField("content",page.getBytes());
    }


    Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.58")
            .addHeader("Referer","http://img.netbian.com/");
            //.addHeader("Host", "broadcast.chat.bilibili.com:7826");
            //.addHeader("GET","wss://broadcast.chat.bilibili.com:7826/sub?platform=web HTTP/1.1")

    @Override
    public Site getSite() {
        return site;
    }
}

class BiliDownloadProcessor implements PageProcessor{

    @Override
    public void process(Page page) {
        System.out.println("访问成功");
        page.putField("content",page.getBytes());
    }


    Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.58")
            .addHeader("Referer","https://www.bilibili.com/")
            //.addHeader("Host", "broadcast.chat.bilibili.com:7826");
            //.addHeader("GET","wss://broadcast.chat.bilibili.com:7826/sub?platform=web HTTP/1.1")
            .addHeader("cookie","buvid3=7A30A229-7736-B2F8-72CA-B23F51446DEB65436infoc; b_nut=1683871666; buvid4=215CDB54-E101-B306-630B-CFC1B618967065436-023051214-2Xv1C9FrK4IlOcrMjh30oA%3D%3D; rpdid=|(m~YkJuJmJ0J'uY)J)~u)m~; CURRENT_FNVAL=4048; _uuid=86AAB6102-F873-61EF-FC9F-39461034A9E8752032infoc; hit-new-style-dyn=0; hit-dyn-v2=1; i-wanna-go-back=-1; LIVE_BUVID=AUTO1716838904547914; nostalgia_conf=-1; buvid_fp_plain=undefined; FEED_LIVE_VERSION=V8; header_theme_version=CLOSE; CURRENT_PID=0ac57290-f7a5-11ed-997b-dde6a7477995; bp_video_offset_188707901=undefined; b_ut=5; DedeUserID=33343273; DedeUserID__ckMd5=1eac6635aeb3ef1e; CURRENT_QUALITY=80; fingerprint=7e1db99d35b86fa19022509ca86ab03e; home_feed_column=4; SESSDATA=938eaace%2C1702906913%2Cf007f%2A62LGRMeW2te9m34SlyZ4wtqUaC1cxBkYhvh5KQTsrELVgpUtjEhQUoUZUP0jsY7bisaO2h2AAASQA; bili_jct=08b33831a60caf09a906df5e31d2264c; sid=7o333y7l; b_lsid=A9F8B7E1_188EACC0957; buvid_fp=994ea30e6a1752c6c97108bfbc2b9424; browser_resolution=1128-596; bp_video_offset_33343273=810586502276841500; PVID=1");
    @Override
    public Site getSite() {
        return site;
    }
}

class DownloadPipeline implements Pipeline{
    private String path;
    DownloadPipeline(String p){
        path = p;
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        byte[] msg = resultItems.get("content");
        SpiderUtil.byteDownload(path,msg,false);
    }
}
