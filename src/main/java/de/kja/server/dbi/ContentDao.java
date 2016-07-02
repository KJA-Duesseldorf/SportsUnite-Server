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

	public static final String TABLE_CONTENTS = "contents";
	
	@SqlUpdate("insert into " + TABLE_CONTENTS + " (title, shorttext, text) values (:title, :shortText, :text)")
	public void insert(@BindBean Content content);
	
	@SqlQuery("select * from " + TABLE_CONTENTS)
	public List<Content> getAllContents();
	
	@SqlUpdate("delete from " + TABLE_CONTENTS + " where id = :id")
	public int delete(@Bind long id);
	
	class Mapper implements ResultSetMapper<Content> {
		
		public Mapper() {
			
		}

		@Override
		public Content map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			return new Content(r.getLong("id"), r.getString("title"), r.getString("shorttext"), r.getString("text"));
		}
		
	}
}
