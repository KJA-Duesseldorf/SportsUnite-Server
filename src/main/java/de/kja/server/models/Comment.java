package de.kja.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {
	
	@JsonProperty("id")
	private long id;
	
	@JsonProperty("contentid")
	private long contentId;
	
	@JsonProperty("username")
	private String username;

	@JsonProperty("text")
	private String text;

	@JsonProperty("timestamp")
	private long timestamp;

	public Comment(long contentId, String username, String text) {
		this.contentId = contentId;
		this.username = username;
		this.text = text;
	}

	public Comment(long id, long contentId, String username, String text, long timestamp) {
		this.id = id;
		this.contentId = contentId;
		this.username = username;
		this.text = text;
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getContentId() {
		return contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
