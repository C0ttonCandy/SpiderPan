package com.spiderpan.Bilibili;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.spiderpan.SpiderUtil;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BiliVideo {
    public static void start(String bv,String path) {
        Spider.create(new VideoProcesser())
                .addUrl("https://www.bilibili.com/video/" + bv)
                .addPipeline(new VideoPipeline(path))
                .thread(3)
                .run();
    }

}



class VideoProcesser implements PageProcessor{

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        List<String> content = getHtmlContent(html);
        //System.out.println(content);
        page.putField("content", content);



    }

    public List<String> getHtmlContent(Html html){
        String json = String.valueOf(html.regex("<script>window.__playinfo__=(.*?)</script>"));

        Map<String,Object> map = (Map<String, Object>) JSONObject.parse(json);
        Map<String,Object> data =(Map<String, Object>) map.get("data");
        JSONObject dash = (JSONObject) data.get("dash");

        JSONArray videoList = dash.getJSONArray("video");
        Map<String,Object> video = (Map<String, Object>) videoList.get(0);
        String videoUrl = (String) video.get("base_url");


        JSONArray audioList = dash.getJSONArray("audio");
        Map<String,Object> audio = (Map<String, Object>) audioList.get(0);
        String audioUrl = (String) audio.get("base_url");


        String title = String.valueOf(html.regex("<h1 title=\"(.*?)\" class=\"video-title"));
        return Arrays.asList(new String[]{title, videoUrl, audioUrl});
    }



    Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.58")
            .addHeader("Referer","https://www.google.com/")
            .addHeader("cookie","buvid3=7A30A229-7736-B2F8-72CA-B23F51446DEB65436infoc; b_nut=1683871666; buvid4=215CDB54-E101-B306-630B-CFC1B618967065436-023051214-2Xv1C9FrK4IlOcrMjh30oA%3D%3D; rpdid=|(m~YkJuJmJ0J'uY)J)~u)m~; CURRENT_FNVAL=4048; _uuid=86AAB6102-F873-61EF-FC9F-39461034A9E8752032infoc; hit-new-style-dyn=0; hit-dyn-v2=1; i-wanna-go-back=-1; LIVE_BUVID=AUTO1716838904547914; nostalgia_conf=-1; buvid_fp_plain=undefined; FEED_LIVE_VERSION=V8; header_theme_version=CLOSE; CURRENT_PID=0ac57290-f7a5-11ed-997b-dde6a7477995; bp_video_offset_188707901=undefined; b_ut=5; DedeUserID=33343273; DedeUserID__ckMd5=1eac6635aeb3ef1e; CURRENT_QUALITY=80; fingerprint=7e1db99d35b86fa19022509ca86ab03e; home_feed_column=4; SESSDATA=938eaace%2C1702906913%2Cf007f%2A62LGRMeW2te9m34SlyZ4wtqUaC1cxBkYhvh5KQTsrELVgpUtjEhQUoUZUP0jsY7bisaO2h2AAASQA; bili_jct=08b33831a60caf09a906df5e31d2264c; sid=7o333y7l; b_lsid=A9F8B7E1_188EACC0957; buvid_fp=994ea30e6a1752c6c97108bfbc2b9424; browser_resolution=1128-596; bp_video_offset_33343273=810586502276841500; PVID=1");
    @Override
    public Site getSite() {
        return site;
    }
}

class VideoPipeline implements Pipeline{

    private String path;
    VideoPipeline(String p){
        path = p;
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<String> content =  resultItems.get("content");
        //System.out.println(content);
        String videoPath = path + content.get(0) + ".mp4";
        String audioPath = path + content.get(0) + ".mp3";
        SpiderUtil.fileDownload(videoPath,content.get(1));
        SpiderUtil.fileDownload(audioPath,content.get(2));

        //合并视频文件
        try {
            SpiderUtil.mergeData(path + content.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}