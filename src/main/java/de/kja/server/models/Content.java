package de.kja.server.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
	
	private long id;
	
	@NotNull
	private String district;
	
	private String image;

	@JsonIgnore
	private ContentTranslation translation;
	
	@JsonIgnore
	private boolean isPublic;
	
	public Content() {
		
	}

	public Content(String district, String image, ContentTranslation translation, boolean isPublic) {
		this.id = -1;
		this.district = district;
		this.image = image;
		this.translation = translation;
		this.isPublic = isPublic;
	}

	public Content(long id, String district, String image, ContentTranslation translation, boolean isPublic) {
		this.id = id;
		this.district = district;
		this.image = image;
		this.translation = translation;
		this.isPublic = isPublic;
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
		if(translation != null) {
			return translation.getTitle();
		} else {
			return null;
		}
	}

	@JsonProperty("shortText")
	public String getShortText() {
		if(translation != null) {
			return translation.getShortText();
		} else {
			return null;
		}
	}

	@JsonProperty("text")
	public String getText() {
		if(translation != null) {
			return translation.getText();
		} else {
			return null;
		}
	}

	@JsonProperty("district")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@JsonProperty("image")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@JsonIgnore
	public ContentTranslation getTranslation() {
		return translation;
	}

	@JsonIgnore
	public boolean isPublic() {
		return isPublic;
	}

	@JsonIgnore
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

}
