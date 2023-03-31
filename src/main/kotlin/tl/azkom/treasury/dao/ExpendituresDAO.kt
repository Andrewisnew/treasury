package tl.azkom.treasury.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import tl.azkom.treasury.dao.mappers.ExpenditureRecordMapper
import tl.azkom.treasury.dao.records.ExpenditureRecord
import tl.azkom.treasury.entities.Expenditure
import tl.azkom.treasury.entities.Tag
import tl.azkom.treasury.exceptions.NotFoundException
import java.lang.IllegalArgumentException
import java.sql.Connection
import java.sql.Statement
import java.util.stream.Collectors

@Repository
class ExpendituresDAO(
    @Autowired val jdbcTemplate: JdbcTemplate,
    @Autowired val tagsDAO: TagsDAO,
    @Autowired val expendituresToTagsDAO: ExpendituresToTagsDAO
) {
    fun create(expenditure: Expenditure) : Expenditure {
        for (tag in expenditure.tags) {
            if (!tag.hasId()) {
                throw IllegalArgumentException("Unresolved tag: $tag")
            }
        }

        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ conn: Connection ->
            val preparedStatement =
                conn.prepareStatement(
                    "insert into expenditures(\"name\", \"quantity\", \"unit\") VALUES(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
                )

            preparedStatement.setString(1, expenditure.name)
            expenditure.quantity?.let { preparedStatement.setDouble(2, it) }
            preparedStatement.setString(3, expenditure.unit.name)
            preparedStatement
        }, keyHolder)
        val expenditureId = keyHolder.keys!!["id"] as Long
        for (tag in expenditure.tags) {
            expendituresToTagsDAO.create(expenditureId, tag.id)
        }
        return Expenditure(expenditureId, expenditure.name, expenditure.quantity, expenditure.unit, expenditure.tags)
    }

    fun read(expenditureId: Long): Expenditure {
        val expenditure: ExpenditureRecord = (jdbcTemplate.queryForObject(
            "select * from expenditures where id=?",
            ExpenditureRecordMapper(),
            expenditureId
        ) ?: throw NotFoundException("expenditure with id=$expenditureId is not found"))

        return fillExpenditure(expenditure)
    }

    private fun fillExpenditure(
        expenditure: ExpenditureRecord
    ): Expenditure {
        val tagIds = expendituresToTagsDAO.readTagIdsByExpenditureId(expenditure.id)
        val tags = tagIds.stream()
            .map(tagsDAO::read)
            .collect(Collectors.toList())
        return Expenditure(expenditure.id, expenditure.name, expenditure.quantity, expenditure.unit, tags)
    }

    fun readAll(): List<Expenditure> {
        val expenditureRecords = jdbcTemplate.query("select * from expenditures", ExpenditureRecordMapper())
        return expenditureRecords.stream()
            .map(this::fillExpenditure)
            .collect(Collectors.toList())
    }

    fun delete(expenditureId: Long) {
        jdbcTemplate.update("delete from expenditures where id=?", expenditureId)
    }
}