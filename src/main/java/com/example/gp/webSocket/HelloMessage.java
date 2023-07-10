package com.example.gp.webSocket;

public class HelloMessage {

	private String nick;
	private String message;
	private String time;





	public HelloMessage() {
	}

	public HelloMessage(String name) {
		this.message = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String name) {
		this.message = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	// 시간 정보를 위한 getter와 setter 추가
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
