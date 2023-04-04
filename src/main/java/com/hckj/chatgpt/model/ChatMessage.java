package com.hckj.chatgpt.model;

public class ChatMessage {
	private MessageType type;
	private String content;
	private String sender;

	public ChatMessage() {
	}

	public ChatMessage(String content) {
		this.type = MessageType.CHAT;
		this.content =content;
		this.sender = "chatGPT";
	}

	public enum MessageType {
		CHAT, JOIN, LEAVE
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "ChatMessage{" + "type=" + type + ", content='" + content + '\'' + ", sender='" + sender + '\'' + '}';
	}
}
