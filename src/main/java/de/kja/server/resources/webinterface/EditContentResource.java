package de.kja.server.resources.webinterface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import de.kja.server.models.ContentTranslation;
import de.kja.server.models.TranslationStatus;

@Path("/webinterface/edit")
@Produces(MediaType.TEXT_HTML)
public class EditContentResource {

	private ContentDao contentDao;
	private Tika tika = new Tika();
	
	public EditContentResource(ContentDao contentDao) {
		this.contentDao = contentDao;
	}

	private TranslationStatus[] createDefaultTranslationStatus() {
		return new TranslationStatus[] {
				new TranslationStatus("de"), 
				new TranslationStatus("en")
		};
	}

	private void fillTranslationStatus(TranslationStatus[] status, long id) {
		for(TranslationStatus translation : status) {
			if(contentDao.getContent(id, translation.getLanguage()) != null) {
				translation.setFinished(true);
			}
		}
	}
	
	@GET
	@PermitAll
	public Response get(@QueryParam("id") String idString) {
		Content content = null;
		TranslationStatus[] status = createDefaultTranslationStatus();
		
		if(idString == null) {
			return Response.status(400).build();
		} else if(idString.equals("new")) {
			content = new Content("", null, null);
			for(TranslationStatus translation : status) {
				translation.setFinished(false);
			}
		} else {
			try {
				long id = Long.valueOf(idString);
				content = contentDao.getContentWithoutTranslation(id);
				if(content == null) {
					return Response.status(404).build();
				}
				fillTranslationStatus(status, id);
			} catch(NumberFormatException e) {
				return Response.status(400).build();
			}
		}
		return Response.ok(new EditContentView(content, status)).build();
	}
	
	@POST
	@PermitAll
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response post(@FormDataParam("id") String idString, @FormDataParam("district") String district, 
			@FormDataParam("button") String button, @FormDataParam("image") InputStream imageStream, 
			@FormDataParam("image") String filename) throws IOException {
		
		Content content = null;
		String image = null;
		TranslationStatus[] status = createDefaultTranslationStatus();
		
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
			content = new Content(district, image, null);
			int id = contentDao.insertContent(content);
			content.setId(id);
		} else {
			try {
				long id = Long.valueOf(idString);
				content = new Content(id, district, image, null);
				int affected;
				if(button.equals("deleteimage")) {
					String savedImage = contentDao.getContentImage(content.getId());
					if(savedImage == null) {
						return Response.status(HttpStatus.NOT_FOUND_404).build();
					}
					Files.delete(Paths.get("images", savedImage + ".png"));
					content.setImage(null);
					affected = contentDao.updateContentImage(content);
					return Response.seeOther(UriBuilder.fromUri("/webinterface/edit?id={arg1}").build(idString)).build();
				} else {
					affected = contentDao.updateContentDistrict(content);
					if(image != null && !image.isEmpty()) {
						affected += contentDao.updateContentImage(content);
					}
				}
				if(affected == 0) {
					return Response.status(404).build();
				}
				fillTranslationStatus(status, content.getId());
			} catch(NumberFormatException e) {
				return Response.status(400).build();
			}
		}
		
		if(button.equals("saveexit")) {
			return Response.seeOther(UriBuilder.fromUri("/webinterface").build()).build();
		}
		return Response.seeOther(UriBuilder.fromUri("/webinterface/edit?id={arg1}").build(content.getId())).build();
	}
	
	@GET
	@Path("text")
	public Response getContentTranslation(@QueryParam("language") String language, @QueryParam("id") long id) {
		if(language == null || language.isEmpty()) {
			return Response.status(HttpStatus.BAD_REQUEST_400).build();
		}
		Content content = contentDao.getContent(id, language);
		if(content == null) {
			content = new Content(id, "", null, new ContentTranslation(id, language, "", "", ""));
		}
		return Response.ok(new EditContentTranslationView(content)).build();
	}
	
	@POST
	@Path("text")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postContentTranslation(@FormParam("id") long contentId, @FormParam("language") String language,
			@FormParam("title") String title, @FormParam("shortText") String shortText, @FormParam("text") String text,
			@FormParam("button") String button) {
		ContentTranslation translation = new ContentTranslation(contentId, language, title, shortText, text);
		if(contentDao.getContent(contentId, language) == null) {
			contentDao.insertContentTranslation(translation);
		} else {
			if(contentDao.updateContentTranslation(translation) == 0) {
				return Response.status(HttpStatus.NOT_FOUND_404).build();
			}
		}
		if(button.equals("savereturn")) {
			return Response.seeOther(UriBuilder.fromUri("/webinterface/edit?id={arg1}").build(contentId)).build();
		} else if(button.equals("saveexit")) {
			return Response.seeOther(UriBuilder.fromUri("/webinterface").build()).build();
		}
		return Response.ok(new EditContentTranslationView(new Content(contentId, "", null, translation))).build();
		
	}
	
}
