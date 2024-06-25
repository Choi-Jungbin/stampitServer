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

        String title = elements.select("h6.tit").text();

        Elements infos = doc.select("ul.cd-info-list li");

    }
}
