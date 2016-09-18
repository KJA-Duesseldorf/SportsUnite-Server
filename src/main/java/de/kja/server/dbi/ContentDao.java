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
import de.kja.server.models.ContentTranslation;

@RegisterMapper(ContentDao.ContentMapper.class)
public interface ContentDao {
	
	public static final String DEFAULT_LANGUAGE = "de";
	
	@SqlQuery("select :language as language, contents.id, districts.name, contents.image, contents.public, "
			+ "contenttranslations.title, contenttranslations.shorttext, contenttranslations.text "
			+ "from contents "
			+ "inner join contenttranslations on contents.id = contenttranslations.contentid "
			+ "inner join districts on contents.districtid = districts.id "
			+ "where contenttranslations.language = :language "
			+ "and (contents.public or :showPrivate)")
	public List<Content> getAllContents(@Bind("language") String language, @Bind("showPrivate") boolean showPrivate);
	
	@SqlQuery("select :language as language, contents.id, districts.name, contents.image, contents.public, "
			+ "contenttranslations.title, contenttranslations.shorttext, contenttranslations.text "
			+ "from contents "
			+ "inner join contenttranslations on contents.id = contenttranslations.contentid "
			+ "inner join districts on contents.districtid = districts.id "
			+ "where contenttranslations.language = :language "
			+ "and (contents.public or :showPrivate) "
			+ "order by ST_Distance(districts.position, (select position from districts where name = :district)) asc;")
	public List<Content> getAllContentsOrdered(@Bind("language") String language, @Bind("district") String district, 
			@Bind("showPrivate") boolean showPrivate);
	
	@SqlQuery("select contenttranslations.language as language, contents.id, districts.name, contents.image, contents.public, "
			+ "contenttranslations.title, contenttranslations.shorttext, contenttranslations.text "
			+ "from contents "
			+ "left outer join contenttranslations on contents.id = contenttranslations.contentid "
			+ "inner join districts on contents.districtid = districts.id")
	public List<Content> getReallyAllContents();
	
	@SqlQuery("select :language as language, contents.id, districts.name, contents.image, contents.public, "
			+ "contenttranslations.title, contenttranslations.shorttext, contenttranslations.text "
			+ "from contents "
			+ "inner join contenttranslations on contents.id = contenttranslations.contentid "
			+ "inner join districts on contents.districtid = districts.id " 
			+ "where contents.id = :id and contenttranslations.language = :language")
	public Content getContent(@Bind("id") long id, @Bind("language") String language);
	
	@SqlQuery("select contents.id, districts.name, contents.image, contents.public "
			+ "from contents "
			+ "inner join districts on contents.districtid = districts.id " 
			+ "where contents.id = :id")
	public Content getContentWithoutTranslation(@Bind("id") long id);
	
	
	@SqlQuery("select contents.image from contents where id = :id")
	public String getContentImage(@Bind("id") long id);
	
	
	
	@SqlQuery("insert into contents (districtid, image) "
			+ "values ((select id from districts where name = :district), :image) "
			+ "returning id")
	public int insertContent(@BindBean Content content);
	
	@SqlUpdate("insert into contenttranslations (contentid, language, title, shorttext, text)"
			+ "values(:contentId, :language, :title, :shortText, :text)")
	public int insertContentTranslation(@BindBean ContentTranslation translation);
	
	@SqlUpdate("delete from contents where id = :id; "
			+ "delete from contentranslations where contentid = :id;")
	public int delete(@Bind("id") long id);
	
	@SqlUpdate("update contents "
			+ "set districtid=(select id from districts "
			+ "where name = :district) where id = :id; ")
	public int updateContentDistrict(@BindBean Content content);
	
	@SqlUpdate("update contents "
			+ "set image=:image where id = :id")
	public int updateContentImage(@BindBean Content content);
	
	@SqlUpdate("update contenttranslations "
			+ "set title=:title, shorttext=:shortText, text=:text "
			+ "where contentid = :contentId and language = :language")
	public int updateContentTranslation(@BindBean ContentTranslation content);
	
	@SqlUpdate("update contents "
			+ "set public = :public "
			+ "where id = :id")
	public int updateContentPublic(@BindBean Content content);
	
	class ContentMapper implements ResultSetMapper<Content> {
		
		public ContentMapper() {
			
		}

		@Override
		public Content map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			ContentTranslation contentTranslation = null;
			if(r.getMetaData().getColumnCount() > 4) {
				contentTranslation = new ContentTranslation(r.getString("language"), 
						r.getString("title"), r.getString("shorttext"), r.getString("text"));
			}
			return new Content(r.getLong("id"), r.getString("name"), r.getString("image"), contentTranslation, r.getBoolean("public"));
		}
		
	}
}
