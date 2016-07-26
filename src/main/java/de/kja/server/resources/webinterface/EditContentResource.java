package de.kja.server.resources.webinterface;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import de.kja.server.dbi.ContentDao;
import de.kja.server.models.Content;

@Path("/webinterface/edit")
@Produces(MediaType.TEXT_HTML)
public class EditContentResource {

	private ContentDao contentDao;
	
	public EditContentResource(ContentDao contentDao) {
		this.contentDao = contentDao;
	}
	
	@GET
	@PermitAll
	public Response get(@QueryParam("id") String idString) {
		Content content = null;
		if(idString == null) {
			return Response.status(400).build();
		} else if(idString.equals("new")) {
			content = new Content("", "", "", "");
		} else {
			try {
				long id = Long.valueOf(idString);
				content = contentDao.getContent(id);
				if(content == null) {
					return Response.status(404).build();
				}
			} catch(NumberFormatException e) {
				return Response.status(400).build();
			}
		}
		return Response.ok(new EditContentView(content)).build();
	}
	
	@POST
	@PermitAll
	public Response post(@FormParam("id") String idString, @FormParam("title") String title,
			@FormParam("shortText") String shortText, @FormParam("text") String text, @FormParam("district") String district, 
			@FormParam("button") String button) {
		Content content = null;
		if(idString == null) {
			return Response.status(400).build();
		} else if(idString.equals("new")) {
			content = new Content(title, shortText, text, district);
			int id = contentDao.insert(content);
			content.setId(id);
		} else {
			try {
				long id = Long.valueOf(idString);
				content = new Content(id, title, shortText, text, district);
				int affected = contentDao.update(content);
				if(affected == 0) {
					return Response.status(404).build();
				}
			} catch(NumberFormatException e) {
				return Response.status(400).build();
			}
		}
		if(button.equals("saveexit")) {
			return Response.seeOther(UriBuilder.fromUri("/webinterface").build()).build();
		}
		return Response.ok(new EditContentView(content)).build();
	}
	
}
