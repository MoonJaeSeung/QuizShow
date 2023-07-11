package com.example.gp.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@Component
public class RestMainController {

    //쿠키를 통해 nick 가져오기
    @GetMapping(value = "/getNick")
    public ResponseEntity<String> getNickForAxios(HttpServletRequest request) {
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

                    JSONObject responseJson = new JSONObject();
                    responseJson.put("nick", nick);

                    return ResponseEntity.ok(responseJson.toString());
                }// 임시회원 nick 가져오기
                else if (cookie.getName().equals("nick")) {
                    String nick = cookie.getValue();

                    // nick 값을 Json 형식으로 응답
                    JSONObject responseJson = new JSONObject();
                    responseJson.put("nick", nick);

                    return ResponseEntity.ok(responseJson.toString());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
//1차시도