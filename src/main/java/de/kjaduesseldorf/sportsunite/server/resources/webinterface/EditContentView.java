package de.kjaduesseldorf.sportsunite.server.resources.webinterface;

import de.kjaduesseldorf.sportsunite.server.models.Content;
import de.kjaduesseldorf.sportsunite.server.models.TranslationStatus;
import io.dropwizard.views.View;

public class EditContentView extends View {

	private Content content;
	private TranslationStatus[] translations;
	
	public EditContentView(Content content, TranslationStatus[] translations) {
		super("editcontent.ftl");
		this.content = content;
		this.translations = translations;
	}

	public Content getContent() {
		return content;
	}

	public TranslationStatus[] getTranslations() {
		return translations;
	}
}
