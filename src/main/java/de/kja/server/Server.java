package de.kja.server;

import org.skife.jdbi.v2.DBI;

import de.kja.server.dbi.ContentDao;
import de.kja.server.resources.service.ContentResource;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
	}

	@Override
	public void run(ServerConfig configuration, Environment environment) throws Exception {
		final DBIFactory factory = new DBIFactory();
		final DBI dbi = factory.build(environment, configuration.getDatabase(), "postgresql");
		final ContentDao contentDao = dbi.onDemand(ContentDao.class);
		
		final ContentResource contentResource = new ContentResource(contentDao);
		environment.jersey().register(contentResource);
	}

}
