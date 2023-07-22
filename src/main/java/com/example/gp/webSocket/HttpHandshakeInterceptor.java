package com.example.gp.webSocket;

import com.example.gp.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public HttpHandshakeInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
//        Cookie[] cookies = req.getCookies();
//
//        // 쿠키에서 닉네임을 가져와 세션에 추가
//        String nick = getNickFromCookie(cookies);
//        if (nick != null) {
//            attributes.put("nick", nick);
//        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {

    }

    // 쿠키에서 닉네임을 추출하는 메서드
    private String getNickFromCookie(Cookie[] cookies) {
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

                        return claims.getSubject();
                    }
                } else if (cookie.getName().equals("nick")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}


