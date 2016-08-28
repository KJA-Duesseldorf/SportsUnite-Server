package de.kja.server.resources.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import de.kja.server.dbi.UserDao;

@Path("/service/v1/register")
public class RegisterResource {
	
	private UserDao userDao;
	
	public RegisterResource(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@POST
	public Response register(@FormParam("name") String name, @FormParam("password") String password) {
		if(!name.matches("[\\u\\l][\\u\\l_\\-\\d]*")) {
			return Response.status(HttpStatus.BAD_REQUEST_400).build();
		}
		int changed = userDao.addUser(name, password);
		if(changed == 0) {
			return Response.status(HttpStatus.CONFLICT_409).build();
		} else {
			return Response.status(HttpStatus.OK_200).build();
		}
	}

}
