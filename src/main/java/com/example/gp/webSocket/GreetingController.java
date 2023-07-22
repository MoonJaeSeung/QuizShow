package com.example.gp.webSocket;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.util.HtmlUtils;


import java.security.Principal;
import java.util.*;




@Controller
@Slf4j
public class GreetingController {

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final Set<String> connectedUserIds = new HashSet<>();
	private final Map<String, String> userNicknames = new HashMap<>();
	private final Map<String, String> nickToSessionId = new HashMap<>(); //



	@Autowired
	public GreetingController(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;

	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
		String nickname = message.getNick();
		String time = message.getTime();
		String greetingMessage = "<div class='message'><span class='nick'>" + nickname + "</span> : <span class='text'>"
				+ HtmlUtils.htmlEscape(message.getMessage()) + "</span> <span class='time'>  " + time + "</span></div>";
		return new Greeting(greetingMessage);
	}

	private void broadcastConnectedUserNicknames() {
		List<String> nicknames = new ArrayList<>(userNicknames.values());
		simpMessagingTemplate.convertAndSend("/topic/userList", nicknames);
	}

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = headerAccessor.getSessionId();
		String nickname = (String) headerAccessor.getNativeHeader("nick").get(0);
		connectedUserIds.add(sessionId);
		userNicknames.put(sessionId, nickname);
		nickToSessionId.put(nickname, sessionId); // 새로운 맵에 닉네임과 세션 ID 저장
		log.info(nickname + " !!!!!!!!!");
		broadcastConnectedUserNicknames();
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = headerAccessor.getSessionId();
		connectedUserIds.remove(sessionId);
		String nickname = userNicknames.remove(sessionId);
		nickToSessionId.remove(nickname); // 연결이 끊어진 사용자의 닉네임과 세션 ID를 맵에서 제거
		broadcastConnectedUserNicknames();
	}

	@MessageMapping("/requestUsers")
	public void requestConnectedUsers() {
		broadcastConnectedUserNicknames();
	}




	@MessageMapping("/requestSessionId")
	public void handleRequestSessionId(StompHeaderAccessor headerAccessor) {
		String sessionId = headerAccessor.getSessionId();

		if (sessionId != null) {
			log.info(sessionId + " haha");
			simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/sessionId", sessionId);
		}
	}


}