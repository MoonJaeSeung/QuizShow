package com.example.gp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class NewsDto {

    // 분야
    private String category;

    // 기사 제목
    private String title;

    // 기사 사진
    private String photo;

    // 기사 url
    private String url;

}
