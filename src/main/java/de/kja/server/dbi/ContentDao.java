package de.kja.server.dbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import de.kja.server.models.Content;

@RegisterMapper(ContentDao.Mapper.class)
public interface ContentDao {
	
	@SqlQuery("insert into contents (title, shorttext, text, districtid, image) "
			+ "values (:title, :shortText, :text, (select id from districts where name = :district), :image) "
			+ "returning id")
	public int insert(@BindBean Content content);
	
	@SqlQuery("select contents.id, contents.title, contents.shortText, contents.text, districts.name, contents.image "
			+ "from contents " 
			+ "inner join districts on contents.districtid = districts.id")
	public List<Content> getAllContents();
	
	@SqlQuery("select contents.id, contents.title, contents.shortText, contents.text, districts.name, contents.image "
			+ "from contents "
			+ "inner join districts ON contents.districtid = districts.id "
			+ "order by ST_Distance(districts.position, (select position from districts where name = :district)) asc;")
	public List<Content> getAllContentsOrdered(@Bind("district") String district);
	
	@SqlQuery("select contents.id, contents.title, contents.shortText, contents.text, districts.name, contents.image "
			+ "from contents "
			+ "inner join districts on contents.districtid = districts.id " 
			+ "where contents.id = :id")
	public Content getContent(@Bind("id") long id);
	
	@SqlUpdate("delete from contents where id = :id")
	public int delete(@Bind("id") long id);
	
	@SqlUpdate("update contents "
			+ "set title=:title, shortText=:shortText, text=:text, districtid=(select id from districts "
			+ "where name = :district) where id = :id")
	public int update(@BindBean Content content);
	
	@SqlUpdate("update contents "
			+ "set title=:title, shortText=:shortText, text=:text, districtid=(select id from districts "
			+ "where name = :district), image=:image where id = :id")
	public int updateImage(@BindBean Content content);
	
	@SqlQuery("select contents.image from contents where id = :id")
	public String getImage(@Bind("id") long id);
	
	class Mapper implements ResultSetMapper<Content> {
		
		public Mapper() {
			
		}

		@Override
		public Content map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			return new Content(r.getLong("id"), r.getString("title"), r.getString("shorttext"),
					r.getString("text"), r.getString("name"), r.getString("image"));
		}
		
	}
}
