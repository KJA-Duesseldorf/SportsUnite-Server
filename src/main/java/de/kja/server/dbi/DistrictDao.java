package de.kja.server.dbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import de.kja.server.models.District;

@RegisterMapper(DistrictDao.Mapper.class)
public interface DistrictDao {
	
	@SqlQuery("select name from districts")
	public List<String> getAllDistrictNames();
	
	@SqlQuery("select id, name, subdistricts from districts")
	public List<District> getAllDistricts();
	
	/**
	 * Checks if a district is known, i.e. valid.
	 * @param district the district's name
	 * @return 0 if this district is unknown and something else otherwise
	 */
	@SqlQuery("select count(*) from districts where name = :district")
	public int isValid(@Bind("district") String district);
	
	class Mapper implements ResultSetMapper<District> {
		
		public Mapper() {
			
		}

		@Override
		public District map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			return new District(r.getLong("id"), r.getString("name"), r.getString("subdistricts"));
		}
		
	}

}
