package de.kja.server.resources.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import de.kja.server.dbi.UserDao;
import de.kja.server.util.PasswordAuthentication;

@Path("/service/v1/register")
public class RegisterResource {
	
	private UserDao userDao;
	private PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
	
	public RegisterResource(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response isUsed(@QueryParam("name") String name) {
		if(!name.matches("[A-Za-z][A-Za-z_\\-0-9]*")) {
			return Response.status(HttpStatus.BAD_REQUEST_400).build();
		}
		int count = userDao.exists(name);
		return Response.ok(String.valueOf(count != 0)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response register(@FormParam("name") String name, @FormParam("password") String password) {
		if(name == null || name.isEmpty() || password == null || password.isEmpty() || !name.matches("[A-Za-z][A-Za-z_\\-0-9]*")) {
			return Response.status(HttpStatus.BAD_REQUEST_400).build();
		}
		int changed = userDao.addUser(name, passwordAuthentication.hash(password.toCharArray()));
		if(changed == 0) {
			return Response.status(HttpStatus.CONFLICT_409).build();
		} else {
			return Response.status(HttpStatus.OK_200).build();
		}
	}

}
