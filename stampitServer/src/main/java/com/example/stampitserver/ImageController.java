package com.example.stampitserver;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ResourceLoader resourceLoader;
    
    @Value("${img.contest}")
    private String imgPath;

    @GetMapping("/img/contest/{filename}")
    public ResponseEntity<Resource> getImg(@PathVariable String filename) throws MalformedURLException {
        Resource img = resourceLoader.getResource("file:" + imgPath + filename);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpg");

        return new ResponseEntity<>(img, headers, HttpStatus.OK);
    }
}
