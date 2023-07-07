package com.example.gp.controller;


import com.example.gp.dto.NewsDto;
import com.example.gp.entity.Celeb;
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
    public String gameView1(Model model, HttpServletRequest request){
        //사자성어 문제 가져오기
        List<Word> words = gameService.findAllWord();
        model.addAttribute("words", words);

        extracted(model, request);

        return "content/game1";
    }
    // 인물 퀴즈
    @GetMapping("/game2")
    public String gameView2(Model model,HttpServletRequest request) {
        extracted(model, request);
        return "content/game2";
    }

    @GetMapping("/game2/celeb")
    @ResponseBody
    public List<Celeb> getCelebData() {
        return gameService.findAllCeleb();
    }

    @GetMapping("/nick")
    @ResponseBody
    public String getNick(Model model,HttpServletRequest request){
        extracted(model, request);
        String nick = (String)model.getAttribute("nick");
        return nick;
    }

    @GetMapping("/news")
    public String news(Model model,HttpServletRequest request) {
        extracted(model, request);
        return "content/news";
    }

    @GetMapping("/newsDetail")
    public String newsDetail(@RequestParam("category") int category,Model model) throws IOException {
        log.info("before");
        List<NewsDto> news = newsService.getNews(category);
        log.info("after");
        model.addAttribute("news", news);
        return "content/newsDetail";
    }



    //쿠키를 통해 nick 가져오기
    private static void extracted(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        //쿠키에서 jwt 토큰 가져오기
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("jwt")) {
                    String jwtToken = cookie.getValue();

                    //서명 검증과 파싱을 동시에 수행
                    Jws<Claims> claimsJws = Jwts.parser()
                            .setSigningKey("mySecretKey")
                            .parseClaimsJws(jwtToken);

                    Claims claims = claimsJws.getBody();

                    String nick = claims.getSubject();
                    model.addAttribute("nick", nick);
                }// 임시회원 nick 가져오기
                else if (cookie.getName().equals("nick")) {
                    String nick = cookie.getValue();
                    model.addAttribute("nick", nick);
                }
            }
        }
    }

    //게임 기록
    @PostMapping ("/record/add")
    public String addRecord(@RequestBody Record record){
        //기록 저장
        recordService.save(record);

        return "choice";
    }



    @GetMapping("/record")
    public String viewRecord(@RequestParam("value") int gameNum,Model model){
        List<Record> records = recordService.find5ByGame(gameNum); //게임종류별 기록 top10 가져오기
        model.addAttribute("records",records);
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
        Word savedWord = gameService.addWord(word);
        return "/admin/addWord";
    }
}

//와우
//main

//test1-2