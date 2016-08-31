package de.kja.server.auth;


import com.google.common.base.Optional;

import de.kja.server.auth.User.AccessLevel;
import de.kja.server.dbi.AdminDao;
import de.kja.server.dbi.UserDao;
import de.kja.server.util.PasswordAuthentication;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class DatabaseAuthenticator implements Authenticator<BasicCredentials, User> {
	
	private UserDao userDao;
	private AdminDao adminDao;
	private PasswordAuthentication passwordAuthentication;

	public DatabaseAuthenticator(UserDao userDao, AdminDao adminDao) {
		this.userDao = userDao;
		this.adminDao = adminDao;
		this.passwordAuthentication = new PasswordAuthentication();
	}

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		String hash = userDao.getPassword(credentials.getUsername());
		if(hash != null && passwordAuthentication.authenticate(credentials.getPassword().toCharArray(), hash)) {
			return Optional.of(new User(credentials.getUsername(), AccessLevel.USER));
		}
		hash = adminDao.getPassword(credentials.getUsername());
		if(hash != null && passwordAuthentication.authenticate(credentials.getPassword().toCharArray(), hash)) {
			return Optional.of(new User(credentials.getUsername(), AccessLevel.ADMIN));
		}
		return Optional.absent();
	}

}
