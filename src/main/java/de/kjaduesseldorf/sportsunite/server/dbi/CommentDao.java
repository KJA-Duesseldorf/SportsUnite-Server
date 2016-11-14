package de.kjaduesseldorf.sportsunite.server.dbi;

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

import de.kjaduesseldorf.sportsunite.server.models.Comment;

@RegisterMapper(CommentDao.Mapper.class)
public interface CommentDao {

	@SqlQuery("select comments.id as id, comments.contentid as contentid, users.name as username, "
			+ "comments.text as text, comments.timestamp as timestamp "
			+ "from comments "
			+ "join users on comments.userid = users.id "
			+ "where comments.contentid = :contentid "
			+ "order by comments.timestamp asc")
	public List<Comment> getComments(@Bind("contentid") long contentId);
	
	@SqlUpdate("insert into comments (contentid, userid, text) "
			+ "values (:contentId, "
			+ "(select id from users where name = :username), "
			+ ":text)")
	public int addComment(@BindBean Comment comment);
	
	class Mapper implements ResultSetMapper<Comment> {
		
		public Mapper() {}

		@Override
		public Comment map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			return new Comment(r.getLong("id"), r.getLong("contentid"), r.getString("username"), 
					r.getString("text"), r.getTimestamp("timestamp").getTime());
		}
		
	}

}
