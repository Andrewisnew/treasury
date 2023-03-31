package tl.azkom.treasury.dao.mappers

import org.springframework.jdbc.core.RowMapper
import tl.azkom.treasury.dao.records.ExpenditureRecord
import tl.azkom.treasury.entities.ExpenditureUnit
import java.sql.ResultSet

class ExpenditureRecordMapper : RowMapper<ExpenditureRecord> {
    override fun mapRow(rs: ResultSet, rowNum: Int): ExpenditureRecord {
        return ExpenditureRecord(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDouble("quantity"),
            ExpenditureUnit.valueOf(rs.getString("unit"))
        );
    }
}