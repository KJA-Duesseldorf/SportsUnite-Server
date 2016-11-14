package de.kjaduesseldorf.sportsunite.server.dbi;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface AdminDao {
	
	public static final String TABLE_ADMINS = "admins";
	
	@SqlQuery("select password from " + TABLE_ADMINS + " where name = :name")
	public String getPassword(@Bind("name") String name);

}
