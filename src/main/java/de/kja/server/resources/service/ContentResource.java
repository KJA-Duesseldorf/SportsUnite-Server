package de.kja.server.resources.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;

import de.kja.server.dbi.ContentDao;
import de.kja.server.dbi.DistrictDao;
import de.kja.server.models.Content;

@Path("/service/v1/content")
@Produces(MediaType.APPLICATION_JSON)
public class ContentResource {
	
	private ContentDao contentDao;
	private DistrictDao districtDao;

	public ContentResource(ContentDao contentDao, DistrictDao districtDao) {
		this.contentDao = contentDao;
		this.districtDao = districtDao;
	}
	
	@GET
	public List<Content> getContent(@QueryParam("district") Optional<String> district) {
		List<Content> contents = null;
		if(district.isPresent() && !district.get().isEmpty() && districtDao.isValid(district.get()) != 0) {
			contents = contentDao.getAllContentsOrdered(district.get());
		} else {
			contents = contentDao.getAllContents();
		}
		return contents;
	}

}
