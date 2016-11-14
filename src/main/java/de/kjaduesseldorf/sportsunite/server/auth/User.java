package de.kjaduesseldorf.sportsunite.server.auth;

import java.security.Principal;

public class User implements Principal {
	
	public enum AccessLevel {
		USER("user"), ADMIN("admin");
		
		private String role;
		
		AccessLevel(String role) {
			this.role = role;
		}
		
		public String getRole() {
			return role;
		}
	}
	
	private String name;
	private AccessLevel accessLevel;

	public User(String name, AccessLevel accessLevel) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

}
