package com.example.gp.service;

import com.example.gp.dto.NewsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class NewsServiceTest {

    @Autowired
    NewsService newsService;


    @Test
    public void getNews() throws IOException {
        List<NewsDto> news = newsService.getNews(100);
        log.info(String.valueOf(news));
    }

}