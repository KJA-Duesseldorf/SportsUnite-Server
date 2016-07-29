package de.kja.server.resources.webinterface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.tika.Tika;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.media.multipart.FormDataParam;

import de.kja.server.dbi.ContentDao;
import de.kja.server.models.Content;

@Path("/webinterface/edit")
@Produces(MediaType.TEXT_HTML)
public class EditContentResource {

	private ContentDao contentDao;
	private Tika tika = new Tika();
	
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
			content = new Content("", "", "", "", null);
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
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response post(@FormDataParam("id") String idString, @FormDataParam("title") String title,
			@FormDataParam("shortText") String shortText, @FormDataParam("text") String text, 
			@FormDataParam("district") String district, @FormDataParam("button") String button, 
			@FormDataParam("image") InputStream imageStream, @FormDataParam("image") String filename) throws IOException {
		Content content = null;
		String image = null;
		
		if(idString == null) {
			return Response.status(400).build();
		}
		if(filename != null && !filename.isEmpty()) {
			image = UUID.randomUUID().toString();
			java.nio.file.Path outputPath = FileSystems.getDefault().getPath("images", image + ".png");
			Files.copy(imageStream, outputPath);
			if(!tika.detect(outputPath).equals("image/png")) {
				return Response.status(HttpStatus.BAD_REQUEST_400).build();
			}
		}
		
		if(idString.equals("new")) {
			content = new Content(title, shortText, text, district, image);
			int id = contentDao.insert(content);
			content.setId(id);
		} else {
			try {
				long id = Long.valueOf(idString);
				content = new Content(id, title, shortText, text, district, image);
				int affected;
				if(button.equals("deleteimage")) {
					String savedImage = contentDao.getImage(content.getId());
					if(savedImage == null) {
						return Response.status(HttpStatus.NOT_FOUND_404).build();
					}
					Files.delete(Paths.get("images", savedImage + ".png"));
					content.setImage(null);
					affected = contentDao.updateImage(content);
					return Response.seeOther(UriBuilder.fromUri("/webinterface/edit?id={arg1}").build(idString)).build();
				} else if(image == null) {
					affected = contentDao.update(content);
				} else {
					affected = contentDao.updateImage(content);
				}
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
