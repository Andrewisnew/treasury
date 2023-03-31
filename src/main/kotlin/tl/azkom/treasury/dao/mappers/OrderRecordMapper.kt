package tl.azkom.treasury.dao.mappers

import org.springframework.jdbc.core.RowMapper
import tl.azkom.treasury.dao.records.ExpenditureRecord
import tl.azkom.treasury.dao.records.OrderRecord
import tl.azkom.treasury.entities.ExpenditureUnit
import java.sql.ResultSet

class OrderRecordMapper : RowMapper<OrderRecord> {
    override fun mapRow(rs: ResultSet, rowNum: Int): OrderRecord {
        return OrderRecord(
            rs.getLong("id"),
            rs.getLong("expenditure_id"),
            rs.getLong("provider_id"),
            rs.getDouble("quantity"),
            rs.getDouble("cost"),
            rs.getTimestamp("transaction_timestamp"),
        )
    }
}