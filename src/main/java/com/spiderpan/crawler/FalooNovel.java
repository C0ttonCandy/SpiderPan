package com.spiderpan.crawler;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class FalooNovel {
    public static void start(String path,String url,int pageNum){
        for(int i=1; i<=pageNum; i++ ){
            String conUrl = "https://b.faloo.com/" + url +"_" + i +".html";
            Spider.create(new NovelProcesser())
                    .addUrl(conUrl)
                    .addPipeline(new NovelPipeline(path))
                    .thread(10)
                    .run();
        }
    }

}

class NovelProcesser implements PageProcessor{

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String title = html.css("a#novelName").regex("title=\"(.*?)\"").toString();
        String ChapterName = html.css("div.c_l_title h1").regex("<h1>(.*?)</h1>").toString();
        List<String> res = html.css("div.noveContent p").regex("<p>(.*?)</p>").all();

        String content = StringUtils.join(res,"\n");
        //System.out.println(content);
        page.putField("content",content);
        page.putField("title",title);
        page.putField("ChapterName",ChapterName);
    }

    @Override
    public Site getSite() {
        return Site.me()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.58")
                .addHeader("referer","https://b.faloo.com/");
    }
}

class NovelPipeline implements Pipeline{
    String path;
    NovelPipeline(String p){
        path = p;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String content = resultItems.get("content");
        String title = resultItems.get("title");
        String ChapterName = resultItems.get("ChapterName");
        content = ChapterName + "\n\n" + content + "\n\n\n\n";
        SpiderUtil.byteDownload(path + title +".txt",content.getBytes(StandardCharsets.UTF_8),true);

    }
}
