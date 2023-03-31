package tl.azkom.treasury.dao.mappers

import org.springframework.jdbc.core.RowMapper
import tl.azkom.treasury.entities.Tag
import java.sql.ResultSet

class TagMapper : RowMapper<Tag> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Tag {
        return Tag(rs.getLong("id"), rs.getString("name"));
    }
}
