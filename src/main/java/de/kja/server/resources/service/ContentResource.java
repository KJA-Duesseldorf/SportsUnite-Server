package de.kja.server.resources.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kja.server.dbi.ContentDao;
import de.kja.server.models.Content;

@Path("/service/content")
@Produces(MediaType.APPLICATION_JSON)
public class ContentResource {
	
	private ContentDao contentDao;

	public ContentResource(ContentDao contentDao) {
		this.contentDao = contentDao;
	}
	
	@GET
	public List<Content> getContent() {
		List<Content> contents = contentDao.getAllContents();
		return contents;
	}

}
