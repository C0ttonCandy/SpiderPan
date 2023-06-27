package com.spiderpan.crawler;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

public class BianWallpaper {

    public static void start(String path,String cata,int pageNum){
        String url = "http://www.netbian.com/";

        switch (cata){
            case "美女":
            case "meinv":url+="meinv/"; break;
            case "动漫":
            case "dongman":url+="dongman/"; break;
            case "风景":
            case "fengjing":url+="fengjing/"; break;
            case "动物":
            case "dongwu":url+="dongwu/"; break;
            case "建筑":
            case "jianzhu":url+="jianzhu/"; break;
            default:
                System.out.println("壁纸输入格式违规！");
        }
        for(int i=0; i<pageNum; i++){
            int page = i+2;
            String pageUrl = url+"index_"+ page +".htm";
            Spider.create(new WallProcessor())
                    .addUrl(pageUrl)
                    .addPipeline(new WallPipeline(path))
                    .thread(5)
                    .run();
        }
    }
}

class WallProcessor implements PageProcessor {

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String url = String.valueOf(page.getUrl().all());
        if(url.contains("desk")){
            String downloadUrl = html.css("div.pic a img").regex("src=\"(.*?)\"").toString();
            String picTitle = html.css("div.pic a img").regex("title=\"(.*?)\"").toString();

            page.putField("url",downloadUrl);
            page.putField("title",picTitle);
        }
        else{
            List<String> picUrl =  html.css("div.list ul li a[href~=desk]").links().all();
            //String.valueOf(html.regex("<a href=\"/desk(.*?)\" title=\".*?\" target=\"_blank\"><img src=\".*?\" alt=\"(.*?)\""));

            page.addTargetRequests(picUrl);

        }

    }

    @Override
    public Site getSite() {
        return Site.me()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.51");
    }
}

class WallPipeline implements Pipeline {

    String path;
    WallPipeline(String p){
        path = p;
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.get("url");
        String title = resultItems.get("title");
        System.out.println(title+"~~~"+url+ "已在下载中，请稍候···");
        if(url == null) return;
        //SpiderUtil.commenDownload(path + title + ".jpg",url);
        SpiderUtil.WallPaperDownload(path + title + ".jpg",url);
    }
}