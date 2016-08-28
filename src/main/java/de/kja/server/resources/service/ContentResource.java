package de.kja.server.resources.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.jetty.http.HttpStatus;

import com.google.common.base.Optional;

import de.kja.server.dbi.CommentDao;
import de.kja.server.dbi.ContentDao;
import de.kja.server.dbi.DistrictDao;
import de.kja.server.models.Comment;
import de.kja.server.models.Content;

@Path("/service/v1/content")
@Produces(MediaType.APPLICATION_JSON)
public class ContentResource {
	
	private ContentDao contentDao;
	private DistrictDao districtDao;
	private CommentDao commentDao;

	public ContentResource(ContentDao contentDao, DistrictDao districtDao, CommentDao commentDao) {
		this.contentDao = contentDao;
		this.districtDao = districtDao;
		this.commentDao = commentDao;
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
	
	@GET
	@Path("{id}")
	public Response getComments(@PathParam("id") long id) {
		if(contentDao.getContent(id) == null) {
			return Response.status(HttpStatus.NOT_FOUND_404).build();
		}
		List<Comment> comments = commentDao.getComments(id);
		return Response.ok(comments).build();
	}
	
	@POST
	@Path("{id}")
	@RolesAllowed("user")
	public Response postComment(@PathParam("id") long id, @Context SecurityContext securityContext,
			@FormParam("text") String text) {
		if(contentDao.getContent(id) == null) {
			return Response.status(HttpStatus.NOT_FOUND_404).build();
		}
		int changed = commentDao.addComment(new Comment(id, securityContext.getUserPrincipal().getName(), text));
		if(changed == 0) {
			return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
		} else {
			return Response.ok().build();
		}
	}

}
