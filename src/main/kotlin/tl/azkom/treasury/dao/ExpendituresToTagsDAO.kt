package tl.azkom.treasury.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ExpendituresToTagsDAO(@Autowired val jdbcTemplate: JdbcTemplate) {
    fun create(expenditureId: Long, tagId: Long) {
        jdbcTemplate.update(
            "insert into expenditures_to_tags(\"tag_id\", \"expenditure_id\") VALUES(?, ?)",
            tagId,
            expenditureId
        )
    }

    fun readTagIdsByExpenditureId(expenditureId: Long): List<Long> {
        return jdbcTemplate.query(
            "select * from expenditures_to_tags where expenditure_id=?",
            { rs, _ -> rs.getLong("tag_id") },
            expenditureId
        )
    }

    fun delete(expenditureId: Long, tagId: Long) {
        jdbcTemplate.update("delete from expenditures where expenditure_id=? and tag_id=?", expenditureId, tagId)
    }
}