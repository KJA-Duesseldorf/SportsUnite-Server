package de.kja.server.resources.webinterface;

import java.util.Iterator;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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
	@PermitAll
	public IndexView get() {
		List<Content> contents = contentDao.getReallyAllContents();
		for(Iterator<Content> it = contents.iterator(); it.hasNext();) {
			Content content = it.next();
			if(!(content.getTranslation().getLanguage() == null ||
					content.getTranslation().getLanguage().equals(ContentDao.DEFAULT_LANGUAGE))) {
				it.remove();
			}
		}
		return new IndexView(contents, null);
	}
	
	@POST
	@PermitAll
	public Response delete(@FormParam("id") long id) {
		int affected = contentDao.delete(id);
		if(affected == 0) {
			return Response.status(400).build();
		}
		return Response.seeOther(UriBuilder.fromUri("/webinterface").build()).build();
	}
	
}
