package com.example.stampitserver.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CorsTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // 필요한 초기 설정을 여기에 추가할 수 있습니다.
    }

    // 테스트가 이상하다
    // 포스트맨으로 해보니까 정상 작동하는데 왜 테스트 주제에 지랄일까
    @Test
    public void testCors() throws Exception {
        mockMvc.perform(options("/your-endpoint") // CORS를 테스트할 엔드포인트
                        .header(HttpHeaders.ORIGIN, "http://localhost:3000") // 테스트할 출처
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")) // 요청 메서드
                .andExpect(status().isOk()) // HTTP 200 OK 응답 확인
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000")) // 모든 출처 허용
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE,PATCH")); // 허용된 메서드 확인
    }
}
