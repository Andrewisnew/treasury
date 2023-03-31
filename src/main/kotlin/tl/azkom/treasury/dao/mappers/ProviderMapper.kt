package tl.azkom.treasury.dao.mappers

import org.springframework.jdbc.core.RowMapper
import tl.azkom.treasury.entities.Provider
import java.sql.ResultSet

class ProviderMapper : RowMapper<Provider> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Provider {
        return Provider(rs.getLong("id"), rs.getString("name"), rs.getString("address"));
    }
}