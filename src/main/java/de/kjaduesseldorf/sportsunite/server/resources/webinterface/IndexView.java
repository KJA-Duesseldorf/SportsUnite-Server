package de.kjaduesseldorf.sportsunite.server.resources.webinterface;

import java.util.List;

import de.kjaduesseldorf.sportsunite.server.models.Content;
import io.dropwizard.views.View;

public class IndexView extends View {

	private List<Content> contents;
	private String message;
	
	public IndexView(List<Content> contents, String message) {
		super("index.ftl");
		this.contents = contents;
		this.message = message;
	}
	
	public List<Content> getContents() {
		return contents;
	}

	public String getMessage() {
		return message;
	}

}
