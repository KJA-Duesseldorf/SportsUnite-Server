package de.kjaduesseldorf.sportsunite.server.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class District {
	
	private long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String subDistricts;
	
	public District() {
		
	}

	public District(long id, String name, String subDistricts) {
		this.id = id;
		this.name = name;
		this.subDistricts = subDistricts;
	}

	public District(String name, String subDistricts) {
		this.name = name;
		this.subDistricts = subDistricts;
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("subDistricts")
	public String getSubDistricts() {
		return subDistricts;
	}

	@JsonProperty("subDistricts")
	public void setSubDistricts(String subDistricts) {
		this.subDistricts = subDistricts;
	}
	
}
