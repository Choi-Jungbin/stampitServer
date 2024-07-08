package com.example.stampitserver.crawling;

import com.example.stampitserver.contest.ContestFindAllResponseDTO;
import com.example.stampitserver.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CrawlingController {
    private final CrawlingService crawlingService;

    @PostMapping("/crawling")
    public ResponseEntity<ApiUtils.ApiResult> crawler(String url){
        crawlingService.crawling(url);
        return ResponseEntity.ok(ApiUtils.success("success", null));
    }

    @GetMapping("/contests")
    public ResponseEntity<ApiUtils.ApiResult> findAllContest(@PageableDefault(size = 20) Pageable pageable){
        ContestFindAllResponseDTO contestFindAllResponseDTO = crawlingService.findAllContest(pageable);

        return ResponseEntity.ok(ApiUtils.success(contestFindAllResponseDTO, "success"));
    }
}
