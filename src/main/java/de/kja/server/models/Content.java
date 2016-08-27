package de.kja.server.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
	
	private long id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String shortText;
	
	@NotNull
	private String text;
	
	@NotNull
	private String district;
	
	private String image;
	
	public Content() {
		
	}

	public Content(String title, String shortText, String text, String district, String image) {
		this.id = -1;
		this.title = title;
		this.shortText = shortText;
		this.text = text;
		this.district = district;
		this.image = image;
	}

	public Content(long id, String title, String shortText, String text, String district, String image) {
		super();
		this.id = id;
		this.title = title;
		this.shortText = shortText;
		this.text = text;
		this.district = district;
		this.image = image;
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

	@JsonProperty("district")
	public String getDistrict() {
		return district;
	}

	@JsonProperty("district")
	public void setDistrict(String district) {
		this.district = district;
	}

	@JsonProperty("image")
	public String getImage() {
		return image;
	}

	@JsonProperty("image")
	public void setImage(String image) {
		this.image = image;
	}

}
