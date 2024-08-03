package com.example.stampitserver.crawling;

import com.example.stampitserver.contest.Contest;
import com.example.stampitserver.contest.ContestFindAllResponseDTO;
import com.example.stampitserver.contest.ContestRegisterRequestDTO;
import com.example.stampitserver.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class CrawlingController {
    private final CrawlingService crawlingService;

    @GetMapping("/contests")
    public ResponseEntity<ApiUtils.ApiResult> findAllContest(@PageableDefault(size = 20) Pageable pageable){
        ContestFindAllResponseDTO contestFindAllResponseDTO = crawlingService.findAllContest(pageable);

        return ResponseEntity.ok(ApiUtils.success(contestFindAllResponseDTO, "success"));
    }

    @GetMapping("/contest_detail")
    public ResponseEntity<ApiUtils.ApiResult> findContest(Long id){
        Contest contest = crawlingService.findContest(id);

        return ResponseEntity.ok(ApiUtils.success(contest, "success"));
    }

    @PostMapping("/contest")
    public ResponseEntity<ApiUtils.ApiResult> registerContest(@ModelAttribute ContestRegisterRequestDTO contestDTO, @RequestParam("previewImg") MultipartFile previewImg, @RequestParam("img") MultipartFile img){
        crawlingService.registerContest(contestDTO, previewImg, img);

        return ResponseEntity.ok(ApiUtils.success(null, "success"));
    }
}
