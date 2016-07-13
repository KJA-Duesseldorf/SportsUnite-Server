package de.kja.server.resources.webinterface;

import de.kja.server.models.Content;
import io.dropwizard.views.View;

public class EditContentView extends View {

	private Content content;
	
	public EditContentView(Content content) {
		super("editcontent.ftl");
		this.content = content;
	}
	
	public Content getContent() {
		return content;
	}

}
