package de.kja.server.models;

import javax.validation.constraints.NotNull;

public class ContentTranslation {
	
	private long contentId;
	
	@NotNull
	private String language;
	
	@NotNull
	private String title;
	
	@NotNull
	private String shortText;
	
	@NotNull
	private String text;
	
	public ContentTranslation() {
		
	}
	
	public ContentTranslation(String language, String title, String shortText, String text) {
		this.language = language;
		this.title = title;
		this.shortText = shortText;
		this.text = text;
	}
	
	public ContentTranslation(long contentId, String language, String title, String shortText, String text) {
		this.contentId = contentId;
		this.language = language;
		this.title = title;
		this.shortText = shortText;
		this.text = text;
	}

	public long getContentId() {
		return contentId;
	}

	public String getLanguage() {
		return language;
	}

	public String getTitle() {
		return title;
	}

	public String getShortText() {
		return shortText;
	}

	public String getText() {
		return text;
	}
	
}
