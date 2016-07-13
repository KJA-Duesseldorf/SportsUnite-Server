package de.kja.server.auth;


import com.google.common.base.Optional;

import de.kja.server.dbi.AdminDao;
import de.kja.server.util.PasswordAuthentication;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class DatabaseAuthenticator implements Authenticator<BasicCredentials, Admin> {
	
	private AdminDao adminDao;
	private PasswordAuthentication passwordAuthentication;

	public DatabaseAuthenticator(AdminDao adminDao) {
		this.adminDao = adminDao;
		this.passwordAuthentication = new PasswordAuthentication();
	}

	@Override
	public Optional<Admin> authenticate(BasicCredentials credentials) throws AuthenticationException {
		String hash = adminDao.getPassword(credentials.getUsername());
		if(hash != null && passwordAuthentication.authenticate(credentials.getPassword().toCharArray(), hash)) {
			return Optional.of(new Admin(credentials.getUsername()));
		}
		return Optional.absent();
	}

}
