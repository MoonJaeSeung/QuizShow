package com.example.gp.controller;


import com.example.gp.dto.NewsDto;
import com.example.gp.entity.Record;
import com.example.gp.entity.Word;
import com.example.gp.service.GameService;
import com.example.gp.service.NewsService;
import com.example.gp.service.RecordService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


// restAPI 설계는 RestMainController에서 수행

@Controller
@Slf4j
public class MainController {

    @Autowired
    GameService gameService;

    @Autowired
    RecordService recordService;

    @Autowired
    NewsService newsService;


    //게임 선택
    @GetMapping("/choice")
    public String choice(){
        return "choice";
    }

    //사자성어 게임
    @GetMapping("/game1")
    public String gameView1(){
        log.info("game1Click");  //게임 인기도 조사를 위해 로그 수집
        return "content/game1";
    }

//    //사자성어 게임
//    @GetMapping("/game1")
//    public String gameView1(Model model, HttpServletRequest request){
//        //사자성어 문제 가져오기
//        List<Word> words = gameService.findAllWord();
//        model.addAttribute("words", words);
//
//        return "content/game1";
//    }
    // 인물 퀴즈 게임
    @GetMapping("/game2")
    public String game2Menu() {
        log.info("game2Click");
        return "content/game2Menu";
    }

    // 인물 퀴즈 게임 선택화면
    @GetMapping("game2/view")
    public String game2View(@RequestParam("sex")int sex, Model model) {
        int gender = sex;
        model.addAttribute("gender", gender);
        return "content/game2";
    }

    // 암기력 게임 선택화면
    @GetMapping("/news")
    public String news(Model model,HttpServletRequest request) {
        return "content/game3Menu";
    }

    //  암기력 게임
    @GetMapping("/newsDetail")
    public String newsDetail(@RequestParam("category") int category,Model model) throws IOException {
        log.info("game3Click");
        List<NewsDto> news = newsService.getNews(category);
        model.addAttribute("news", news);
        return "content/game3";
    }

    // 스피드 클릭 게임
    @GetMapping("/game4")
    public String game4(){
        log.info("game4Click");
        return "content/game4";
    }


    //기록화면으로 전환
    @GetMapping("/record")
    public String viewRecord(@RequestParam("value") int gameNum,Model model){
        model.addAttribute("gameNum", gameNum); // 게임 번호를 모델에 추가
        return "record";
    }


    @GetMapping("/addWord")
    public String addWord(){
        return "/admin/addWord";
    }

    @PostMapping("/addWord")
    public String addWord(@RequestParam("fullWord") String fullWord){

        Word word = new Word(fullWord);
        gameService.addWord(word);
        return "/admin/addWord";
    }




}

//2