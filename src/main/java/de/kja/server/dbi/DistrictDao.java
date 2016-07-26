package de.kja.server.dbi;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface DistrictDao {
	
	@SqlQuery("select name from districts")
	public List<String> getAllDistrictNames();
	
	/**
	 * Checks if a district is known, i.e. valid.
	 * @param district the district's name
	 * @return 0 if this district is unknown and something else otherwise
	 */
	@SqlQuery("select count(*) from districts where name = :district")
	public int isValid(@Bind("district") String district);

}
