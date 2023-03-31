package tl.azkom.treasury.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import tl.azkom.treasury.dao.mappers.TagMapper
import tl.azkom.treasury.entities.Tag
import tl.azkom.treasury.exceptions.NotFoundException
import java.lang.Boolean
import java.math.BigDecimal
import java.sql.Connection
import java.sql.Statement
import kotlin.Long

@Repository
class TagsDAO(@Autowired val jdbcTemplate: JdbcTemplate) {
    fun create(tag: Tag) : Tag {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ conn: Connection ->
            val preparedStatement =
                conn.prepareStatement("insert into tags(\"name\") VALUES(?)", Statement.RETURN_GENERATED_KEYS)

            preparedStatement.setString(1, tag.name)
            preparedStatement
        }, keyHolder)
        return Tag(keyHolder.keys!!["id"] as Long, tag.name)
    }

    fun read(tagId: Long): Tag {
        return jdbcTemplate.queryForObject("select * from tags where id=?", TagMapper(), tagId)
            ?: throw NotFoundException("tag with id=$tagId is not found")
    }

    fun readAll(): List<Tag> {
        return jdbcTemplate.query("select * from tags", TagMapper())
    }

    fun delete(tagId: Long) {
        jdbcTemplate.update("delete from tags where id=?", tagId)
    }
}