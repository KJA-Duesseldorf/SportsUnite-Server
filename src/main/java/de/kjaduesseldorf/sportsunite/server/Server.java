package de.kjaduesseldorf.sportsunite.server;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import de.kjaduesseldorf.sportsunite.server.auth.AccessLevelAuthorizer;
import de.kjaduesseldorf.sportsunite.server.auth.DatabaseAuthenticator;
import de.kjaduesseldorf.sportsunite.server.auth.User;
import de.kjaduesseldorf.sportsunite.server.dbi.AdminDao;
import de.kjaduesseldorf.sportsunite.server.dbi.CommentDao;
import de.kjaduesseldorf.sportsunite.server.dbi.ContentDao;
import de.kjaduesseldorf.sportsunite.server.dbi.DistrictDao;
import de.kjaduesseldorf.sportsunite.server.dbi.UserDao;
import de.kjaduesseldorf.sportsunite.server.resources.service.ContentResource;
import de.kjaduesseldorf.sportsunite.server.resources.service.DistrictResource;
import de.kjaduesseldorf.sportsunite.server.resources.service.ImagesResource;
import de.kjaduesseldorf.sportsunite.server.resources.service.RegisterResource;
import de.kjaduesseldorf.sportsunite.server.resources.webinterface.EditContentResource;
import de.kjaduesseldorf.sportsunite.server.resources.webinterface.IndexResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
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
		bootstrap.addBundle(new AssetsBundle("/js"));
	}

	@Override
	public void run(ServerConfig configuration, Environment environment) throws Exception {
		environment.jersey().register(MultiPartFeature.class);
		
		final DBIFactory factory = new DBIFactory();
		final DBI dbi = factory.build(environment, configuration.getDatabase(), "postgresql");
		final DistrictDao districtDao = dbi.onDemand(DistrictDao.class);
		final ContentDao contentDao = dbi.onDemand(ContentDao.class);
		final UserDao userDao = dbi.onDemand(UserDao.class);
		final CommentDao commentDao = dbi.onDemand(CommentDao.class);
		
		environment.jersey().register(new AuthDynamicFeature(
				new BasicCredentialAuthFilter.Builder<User>()
				.setAuthenticator(new DatabaseAuthenticator(userDao, dbi.onDemand(AdminDao.class)))
				.setAuthorizer(new AccessLevelAuthorizer())
				.setRealm("Secured")
				.buildAuthFilter()));
		
		final ImagesResource imagesResource = new ImagesResource();
		environment.jersey().register(imagesResource);
		final RegisterResource registerResource = new RegisterResource(userDao);
		environment.jersey().register(registerResource);
		final DistrictResource districtResource = new DistrictResource(districtDao);
		environment.jersey().register(districtResource);
		final ContentResource contentResource = new ContentResource(contentDao, districtDao, commentDao);
		environment.jersey().register(contentResource);
		
		final IndexResource indexResource = new IndexResource(contentDao);
		environment.jersey().register(indexResource);
		final EditContentResource editContentResource = new EditContentResource(contentDao);
		environment.jersey().register(editContentResource);
	}

}
