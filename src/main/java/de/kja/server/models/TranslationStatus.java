package de.kja.server.models;

public class TranslationStatus {
	
	private final String language;
	private boolean finished = false;
	
	public TranslationStatus(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

}
