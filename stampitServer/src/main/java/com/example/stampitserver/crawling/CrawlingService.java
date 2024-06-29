package com.example.stampitserver.crawling;

import com.example.stampitserver.contest.Applicant;
import com.example.stampitserver.contest.Contest;
import com.example.stampitserver.contest.Field;
import com.example.stampitserver.contest.Prize;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


public class CrawlingService {
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

        String[] date = contestData.get("접수기간").split(" ~ ");

        Contest contest = Contest.builder()
                .contestName(title)
                .fields(parseFields(contestData.get("분야")))
                .applicant(parseApplicants(contestData.get("응모대상")))
                .host(contestData.get("주최/주관"))
                .sponsor(contestData.get("후원/협찬"))
                .receptionStart(parseDate(date[0]))
                .receptionEnd(parseDate(date[1]))
                .prize(Prize.fromString(contestData.get("총 상금")))
                .firstPrize(contestData.get("1등 상금"))
                .url(contestData.get("홈페이지"))
                .content(content.toString())
                .build();
        System.out.println(contest);
    }

    private Set<Field> parseFields(String fields){
        if(fields == null || fields.isEmpty()){
            return new HashSet<>();
        }
        return Arrays.stream(fields.split(", "))
                .map(Field::fromString)
                .collect(Collectors.toSet());
    }

    private Set<Applicant> parseApplicants(String applicants){
        if(applicants == null || applicants.isEmpty()){
            return new HashSet<>();
        }
        return Arrays.stream(applicants.split(", "))
                .map(Applicant::fromString)
                .collect(Collectors.toSet());
    }

    private Date parseDate(String date){
        if(date == null || date.isEmpty()){
            return null;
        }
        return Date.valueOf(date);
    }
}
