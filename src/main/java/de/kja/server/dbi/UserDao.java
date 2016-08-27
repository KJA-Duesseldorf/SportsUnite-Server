package de.kja.server.dbi;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface UserDao {

	@SqlQuery("select password from users where name = :name")
	public String getPassword(@Bind("name") String name);
	
	@SqlUpdate("insert into users values (:name, :password) "
			+ "where not exists (select name from users where name = :name)")
	public int addUser(@Bind("name") String name, @Bind("password") String password);
	
}
