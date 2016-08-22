package de.kja.server.resources.images;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.eclipse.jetty.http.HttpStatus;

import com.google.common.io.Files;

@Path("/images")
@Produces("image/png")
public class ImagesResource {

	@GET
	public Response get(@QueryParam("id") String id) {
		if(id == null || id.isEmpty() || id.indexOf('/') != -1) {
			return Response.status(HttpStatus.BAD_REQUEST_400).build();
		}
		final File file = new File("images/" + id + ".png");
		if(!file.isFile()) {
			return Response.status(HttpStatus.NOT_FOUND_404).build();
		}
		return Response.ok(new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				Files.copy(file, output);
			}
		}).build();
	}
	
}
