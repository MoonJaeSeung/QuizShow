package com.example.gp.webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		String nickname = message.getNick(); //닉네임 가져오기
		String time = message.getTime();
		String greetingMessage = "<div class='message'><span class='nick'>" + nickname + "</span> : <span class='text'>"
				+ HtmlUtils.htmlEscape(message.getMessage()) + "</span> <span class='time'>  " + time + "</span></div>";
		// Thread.sleep(500); // simulated delay
		return new Greeting(greetingMessage);
	}

}
