package de.kja.server.auth;

import de.kja.server.auth.User.AccessLevel;
import io.dropwizard.auth.Authorizer;

public class AccessLevelAuthorizer implements Authorizer<User> {

	@Override
	public boolean authorize(User principal, String role) {
		if(principal.getAccessLevel() == AccessLevel.ADMIN) {
			return true;
		} else if(role.equals(AccessLevel.USER.getRole()) && principal.getAccessLevel() == AccessLevel.USER) {
			return true;
		}
		return false;
	}

}
