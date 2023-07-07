package com.example.gp.service;

import com.example.gp.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {


    public List<NewsDto> getNews(int category) throws IOException {

        List<NewsDto> newsList = new ArrayList<>();

        String url = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=265&sid1=" + category;
        Document doc = Jsoup.connect(url).get();



        for (int j = 1; j < 3; j++) {
            String url2 = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=265&sid1=" + category + "&page=" + j;
            Document doc2 = Jsoup.connect(url2).get();
            Elements elements = doc2.getElementsByAttributeValue("class", "list_body newsflash_body");

            Element element = elements.get(0);
            Elements photoElements = element.getElementsByAttributeValue("class", "photo");



            for (int i = 0; i < photoElements.size(); i++) {
                Element articleElement = photoElements.get(i);
                Elements aElements = articleElement.select("a");
                Element aElement = aElements.get(0);

                String articleUrl = aElement.attr("href");        // 기사링크
                Element imgElement = aElement.select("img").get(0);
                String imgUrl = imgElement.attr("src");            // 사진링크
                String title = imgElement.attr("alt");            // 기사제목

                NewsDto newsDto = new NewsDto();
                newsDto.setTitle(title);
                newsDto.setPhoto(imgUrl);
                newsDto.setUrl(articleUrl);

                newsList.add(newsDto);

                           // 기사내용
                System.out.println("title = " + title);
                System.out.println("imgUrl = " + imgUrl);
                System.out.println("articleUrl = " + articleUrl);
            }
            System.out.println(j + "page 크롤링 종료");
        }
        return newsList;
    }

}

