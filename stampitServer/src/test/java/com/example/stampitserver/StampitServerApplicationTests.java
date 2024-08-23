package com.example.stampitserver;

import com.example.stampitserver.crawling.CrawlingController;
import com.example.stampitserver.crawling.CrawlingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class StampitServerApplicationTests {
    @Autowired
    private CrawlingController crawlingController; // CrawlingController 주입

    @Autowired
    private CrawlingService crawlingService; // CrawlingService 주입

    @Test
    void contextLoads() {
        // 컨트롤러와 서비스가 null이 아닌지 확인
        assertThat(crawlingController).isNotNull();
        assertThat(crawlingService).isNotNull();
    }
}
