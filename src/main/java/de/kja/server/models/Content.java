package de.kja.server.models;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
	
	private long id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String shortText;
	
	@NotBlank
	private String text;
	
	public Content() {
		
	}

	public Content(String title, String shortText, String text) {
		this.id = 0;
		this.title = title;
		this.shortText = shortText;
		this.text = text;
	}

	public Content(long id, String title, String shortText, String text) {
		super();
		this.id = id;
		this.title = title;
		this.shortText = shortText;
		this.text = text;
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("shortText")
	public String getShortText() {
		return shortText;
	}

	@JsonProperty("shortText")
	public void setShortText(String shortText) {
		this.shortText = shortText;
	}

	@JsonProperty("text")
	public String getText() {
		return text;
	}

	@JsonProperty("text")
	public void setText(String text) {
		this.text = text;
	}

}
