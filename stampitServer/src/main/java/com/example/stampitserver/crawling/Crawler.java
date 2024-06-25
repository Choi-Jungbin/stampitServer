package com.example.stampitserver.crawling;

import com.example.stampitserver.contest.Contest;
import com.example.stampitserver.contest.Field;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Crawler {
    public void crawling(String url){
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        }catch (IOException e) {
            e.printStackTrace();
        }

        assert doc != null;
        Elements links = doc.select("div.tit > a");

        for(Element link : links){
            contestCrawling("https://www.wevity.com/" + link.attr("href"));
        }
    }

    private void contestCrawling(String url){
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        }catch (IOException e){
            e.printStackTrace();
        }

        assert doc != null;
        Elements elements = doc.select("div.contest-detail");

        // 공모전 제목
        String title = elements.select("h6.tit").text();

        Elements infos = doc.select("ul.cd-info-list li");

        // 데이터를 저장할 맵 초기화
        Map<String, String> contestData = new HashMap<>();

        for(Element info : infos){
            String tag = info.select("span.tit").text();
            String text;
            if(tag.equals("홈페이지")) text = info.attr("href");
            else text = info.ownText();

            // 맵에 태그와 내용 저장
            contestData.put(tag, text);
        }

        Elements details = elements.select("div.comm-desc div");

        StringBuilder content = new StringBuilder();
        for(Element detail : details){
            content.append(detail.text()).append("\n");
        }

        Contest contest = Contest.builder()
                .contestName()
                .fields()
                .applicant()
                .host()
                .sponsor()
                .receptionStart()
                .receptionEnd()
                .prize()
                .firstPrize()
                .url()
                .content()
                .build();
    }
}
