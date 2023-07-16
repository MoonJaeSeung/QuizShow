package com.example.gp.controller;

import com.example.gp.entity.Celeb;
import com.example.gp.entity.Record;
import com.example.gp.entity.Word;
import com.example.gp.security.JwtTokenProvider;
import com.example.gp.service.GameService;
import com.example.gp.service.RecordService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@Component
public class RestMainController {

    @Autowired
    GameService gameService;

    @Autowired
    RecordService recordService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;



    @GetMapping("/words")
    public ResponseEntity<List<Word>> getWordData(){
        List<Word> words = gameService.findAllWord();

        if (words.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(words);
    }


    // 인물 사진 데이터 가져오기
    @GetMapping("/celebs")
    public ResponseEntity<List<Celeb>> getCelebsByGender(@RequestParam("sex") int sex) {
        List<Celeb> celebs = gameService.findCeleb(sex);

        if (celebs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(celebs);
    }

    @GetMapping("/nick")
    public ResponseEntity<String> getNick(Model model,HttpServletRequest request, JwtTokenProvider jwtTokenProvider){
        extracted(model, request, jwtTokenProvider);
        String nick = (String)model.getAttribute("nick");


        return ResponseEntity.ok(nick);
    }

    //쿠키를 통해 nick 가져오기
    private static void extracted(Model model, HttpServletRequest request, JwtTokenProvider jwtTokenProvider) {
        Cookie[] cookies = request.getCookies();
        // 쿠키에서 jwt 토큰 가져오기
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    String jwtToken = cookie.getValue();

                    // 토큰 유효성 검증
                    if (jwtTokenProvider.validateToken(jwtToken)) {
                        // 서명 검증과 파싱을 동시에 수행
                        Jws<Claims> claimsJws = Jwts.parser()
                                .setSigningKey("mySecretKey")
                                .parseClaimsJws(jwtToken);

                        Claims claims = claimsJws.getBody();

                        String nick = claims.getSubject();
                        model.addAttribute("nick", nick);
                    } else {
                        // Invalid token, handle accordingly
                    }
                } // 임시회원 nick 가져오기
                else if (cookie.getName().equals("nick")) {
                    String nick = cookie.getValue();
                    model.addAttribute("nick", nick);
                }
            }
        }
    }


    //게임 기록 저장
    @PostMapping("/records")
    public ResponseEntity<String> addRecord(@RequestBody Record record){
        //기록 저장
        recordService.save(record);
        log.info("기록 저장 완료: {}", record);

        return ResponseEntity.status(HttpStatus.CREATED).body("기록 저장 완료");
    }

    @GetMapping("/records")
    public ResponseEntity<List<Record>> viewRecords(@RequestParam("value") int gameNum) {
        List<Record> records = recordService.find5ByGame(gameNum); // Retrieve records based on gameNum


        // Set the appropriate HTTP status code and return the records in the response body
        return ResponseEntity.ok().body(records);
    }


}
//1차시도