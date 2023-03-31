package tl.azkom.treasury.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import tl.azkom.treasury.dao.mappers.ProviderMapper
import tl.azkom.treasury.entities.Provider
import tl.azkom.treasury.exceptions.NotFoundException

@Repository
class ProviderDAO(@Autowired val jdbcTemplate: JdbcTemplate) {
    fun create(provider: Provider) {
        jdbcTemplate.update("insert into providers(\"name\", \"address\") VALUES(?, ?)", provider.name, provider.address)
    }

    fun read(providerId: Long): Provider {
        return jdbcTemplate.queryForObject("select * from providers where id=?", ProviderMapper(), providerId)
            ?: throw NotFoundException("Provider with id=$providerId is not found")
    }

    fun readAll(): List<Provider> {
        return jdbcTemplate.query("select * from providers", ProviderMapper())
    }

    fun delete(providerId: Long) {
        jdbcTemplate.update("delete from providers where id=?", providerId)
    }
}