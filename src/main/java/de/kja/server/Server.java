package de.kja.server;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import de.kja.server.auth.User;
import de.kja.server.auth.AccessLevelAuthorizer;
import de.kja.server.auth.DatabaseAuthenticator;
import de.kja.server.dbi.AdminDao;
import de.kja.server.dbi.ContentDao;
import de.kja.server.dbi.DistrictDao;
import de.kja.server.dbi.UserDao;
import de.kja.server.resources.service.ContentResource;
import de.kja.server.resources.service.DistrictResource;
import de.kja.server.resources.service.ImagesResource;
import de.kja.server.resources.webinterface.EditContentResource;
import de.kja.server.resources.webinterface.IndexResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class Server extends Application<ServerConfig> {
	
	public static void main(String[] args) throws Exception {
		new Server().run(args);
	}
	
	@Override
	public void initialize(Bootstrap<ServerConfig> bootstrap) {
		bootstrap.addBundle(new MigrationsBundle<ServerConfig>() {
			@Override
			public PooledDataSourceFactory getDataSourceFactory(ServerConfig configuration) {
				return configuration.getDatabase();
			}
		});
		bootstrap.addBundle(new ViewBundle<ServerConfig>());
	}

	@Override
	public void run(ServerConfig configuration, Environment environment) throws Exception {
		environment.jersey().register(MultiPartFeature.class);
		
		final DBIFactory factory = new DBIFactory();
		final DBI dbi = factory.build(environment, configuration.getDatabase(), "postgresql");
		final DistrictDao districtDao = dbi.onDemand(DistrictDao.class);
		final ContentDao contentDao = dbi.onDemand(ContentDao.class);
		final UserDao userDao = dbi.onDemand(UserDao.class);
		
		environment.jersey().register(new AuthDynamicFeature(
				new BasicCredentialAuthFilter.Builder<User>()
				.setAuthenticator(new DatabaseAuthenticator(userDao, dbi.onDemand(AdminDao.class)))
				.setAuthorizer(new AccessLevelAuthorizer())
				.setRealm("Secured")
				.buildAuthFilter()));
		
		final ImagesResource imagesResource = new ImagesResource();
		environment.jersey().register(imagesResource);
		
		final DistrictResource districtResource = new DistrictResource(districtDao);
		environment.jersey().register(districtResource);
		final ContentResource contentResource = new ContentResource(contentDao, districtDao);
		environment.jersey().register(contentResource);
		
		final IndexResource indexResource = new IndexResource(contentDao);
		environment.jersey().register(indexResource);
		final EditContentResource editContentResource = new EditContentResource(contentDao);
		environment.jersey().register(editContentResource);
	}

}
