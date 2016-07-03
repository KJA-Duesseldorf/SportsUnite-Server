package de.kja.server.resources.webinterface;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kja.server.dbi.ContentDao;
import de.kja.server.models.Content;

@Path("/webinterface")
@Produces(MediaType.TEXT_HTML)
public class IndexResource {
	
	private ContentDao contentDao;
	
	public IndexResource(ContentDao contentDao) {
		this.contentDao = contentDao;
	}

	@GET
	public IndexView get() {
		List<Content> contents = contentDao.getAllContents();
		return new IndexView(contents, null);
	}
	
	@POST
	public IndexView delete(@FormParam("id") long id) {
		String message = null;
		int affected = contentDao.delete(id);
		if(affected == 0) {
			message = "Inhalt, der gelöscht werden sollte, existierte nicht!";
		} else {
			message = "Inhalt gelöscht.";
		}
		List<Content> contents = contentDao.getAllContents();
		return new IndexView(contents, message);
	}
	
}
