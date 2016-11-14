package de.kjaduesseldorf.sportsunite.server;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class ServerConfig extends Configuration {

	@NotNull
	@Valid
	private DataSourceFactory database = new DataSourceFactory();

	@JsonProperty("database")
	public DataSourceFactory getDatabase() {
		return database;
	}

	@JsonProperty("database")
	public void setDatabase(DataSourceFactory database) {
		this.database = database;
	}
}
