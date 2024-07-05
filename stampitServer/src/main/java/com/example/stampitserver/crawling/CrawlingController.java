package com.example.stampitserver.crawling;

import com.example.stampitserver.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
}
