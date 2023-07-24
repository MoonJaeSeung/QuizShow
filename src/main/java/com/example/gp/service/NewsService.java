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

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {


    public List<NewsDto> getNews(int category) throws IOException {

        List<NewsDto> newsList = new ArrayList<>();


        for(int i=1;i<=3;i++) {
            String url = "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=" + category + "&page=" + i;
            Document doc = Jsoup.connect(url).get();


            Elements items = doc.select("li.sh_item");
            for (Element item : items) {
                String title = item.select("a.sh_text_headline").text();
                String imgUrl = item.select("img").attr("src");
                String articleUrl = item.select("a.sh_thumb_link").attr("href");

                NewsDto newsDto = new NewsDto(title, imgUrl, articleUrl);
                newsList.add(newsDto);

            }
        }
        return newsList;

    }

}


