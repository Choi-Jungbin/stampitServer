package com.example.stampitserver.crawling;

import com.example.stampitserver.contest.*;
import com.example.stampitserver.core.error.exception.NotFondEnumException;
import com.example.stampitserver.core.error.exception.NotFoundException;
import com.example.stampitserver.core.error.exception.OutOfDateException;
import com.example.stampitserver.contest.ContestJPARepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final ContestJPARepository contestJPARepository;

    @Value("${contest.url}")
    private String homepage;

    @Value("${img.contest}")
    private String imgPath;

    @Transactional
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
            contestCrawling(homepage + link.attr("href"));
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
            if(tag.equals("홈페이지")) text = info.select("a").attr("href");
            else text = info.ownText();

            // 맵에 태그와 내용 저장
            contestData.put(tag, text);
        }

        Elements details = elements.select("div.comm-desc");

        StringBuilder content = new StringBuilder();
        for(Element detail : details){
            content.append(detail.text()).append("\n");
        }

        String[] date = contestData.get("접수기간").split(" ~ ");

        Contest contest;
        try {
            contest = Contest.builder()
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
        } catch (NotFondEnumException e) {
            System.out.println(e.getMessage());
            System.out.println(title + ", " + contestData.get("홈페이지"));
            return;
        } catch (OutOfDateException e){
            return;
        }
        contestJPARepository.save(contest);

        // 이미지 저장
        String previewImg;
        try{
            String previewImgSrc = elements.select("div.thumb img").attr("src");
            if (previewImgSrc == null || previewImgSrc.isEmpty()) {
                previewImg = null; // 이미지가 없을 경우 null 설정
            } else {
                InputStream previewIn = new URL(homepage + previewImgSrc).openStream();
                previewImg = contest.getId() + "_preview.jpg";
                Files.copy(previewIn, new File(imgPath + previewImg).toPath());
                previewIn.close(); // InputStream 닫기
            }
        } catch (IOException e){
            previewImg = null;
        }
        String img;
        try{
            String imgSrc = details.select("img").attr("src");
            if (imgSrc == null || imgSrc.isEmpty()) {
                img = null; // 이미지가 없을 경우 null 설정
            } else {
                InputStream in = new URL(homepage + imgSrc).openStream();
                img = contest.getId() + ".jpg";
                Files.copy(in, new File(imgPath + img).toPath());
                in.close(); // InputStream 닫기
            }
        } catch (IOException e){
            img = null;
        }
        contest.setImg(previewImg, img);
        contestJPARepository.save(contest);
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

    @Transactional
    public ContestFindAllResponseDTO findAllContest(Pageable pageable){
        Page<Contest> contests = contestJPARepository.findAll(pageable);

        return new ContestFindAllResponseDTO(contests);
    }

    @Transactional
    public Contest findContest(Long id){
        return contestJPARepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id를 가진 공모전이 없습니다"));
    }

    @Transactional
    public void registerContest(ContestRegisterRequestDTO contestDTO, MultipartFile previewImg, MultipartFile img){
        Contest contest;
        try {
            contest = Contest.builder()
                    .contestName(contestDTO.getContestName())
                    .fields(contestDTO.getFields())
                    .applicant(contestDTO.getApplicant())
                    .host(contestDTO.getHost())
                    .sponsor(contestDTO.getSponsor())
                    .receptionStart(contestDTO.getReceptionStart())
                    .receptionEnd(contestDTO.getReceptionEnd())
                    .prize(contestDTO.getPrize())
                    .firstPrize(contestDTO.getFirstPrize())
                    .url(contestDTO.getUrl())
                    .content(contestDTO.getContent())
                    .build();
        } catch (OutOfDateException e){
            return;
        }
        contestJPARepository.save(contest);

        // 이미지 저장
        String previewImgName = null;
        if(!previewImg.isEmpty()){
            try {
                previewImgName = contest.getId() + "_preview.jpg";
                File previewImgFile = new File(imgPath, previewImgName);

                previewImg.transferTo(previewImgFile);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        String imgName = null;
        if(!img.isEmpty()){
            try {
                imgName = contest.getId() + ".jpg";
                File imgFile = new File(imgPath, imgName);

                previewImg.transferTo(imgFile);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        contest.setImg(previewImgName, imgName);
        contestJPARepository.save(contest);
    }
}
