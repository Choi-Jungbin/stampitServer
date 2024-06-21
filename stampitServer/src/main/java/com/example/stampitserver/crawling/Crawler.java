package com.example.stampitserver.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Crawler {
    public void crawling(String url){
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        }catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = doc.select("");
    }
}
