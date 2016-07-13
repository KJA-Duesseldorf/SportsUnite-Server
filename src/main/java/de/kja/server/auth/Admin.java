package de.kja.server.auth;

import java.security.Principal;

public class Admin implements Principal {
	
	private String name;

	public Admin(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
