package de.kjaduesseldorf.sportsunite.server.resources.webinterface;

import de.kjaduesseldorf.sportsunite.server.models.Content;
import io.dropwizard.views.View;

public class EditContentTranslationView extends View {

	private Content content;
	
	public EditContentTranslationView(Content content) {
		super("editcontenttranslation.ftl");
		this.content = content;
	}
	
	public Content getContent() {
		return content;
	}

}
