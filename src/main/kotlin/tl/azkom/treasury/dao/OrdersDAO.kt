package tl.azkom.treasury.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import tl.azkom.treasury.dao.mappers.OrderRecordMapper
import tl.azkom.treasury.dao.records.OrderRecord
import tl.azkom.treasury.entities.Order
import java.sql.Connection
import java.sql.Statement
import java.sql.Timestamp
import java.time.Instant
import java.util.stream.Collectors

@Repository
class OrdersDAO(
    @Autowired val jdbcTemplate: JdbcTemplate,
    @Autowired val providerDAO: ProviderDAO,
    @Autowired val expendituresDAO: ExpendituresDAO
) {
    fun create(order: Order) : Order {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ conn: Connection ->
            val preparedStatement =
                conn.prepareStatement(
                    "insert into orders(\"expenditure_id\", \"provider_id\", \"quantity\", \"cost\", \"transaction_timestamp\") VALUES(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
                )

            preparedStatement.setLong(1, order.expenditure.id)
            preparedStatement.setLong(2, order.provider.id)
            preparedStatement.setDouble(3, order.quantity)
            preparedStatement.setDouble(4, order.cost)
            preparedStatement.setTimestamp(5, Timestamp(order.transactionTime.toEpochMilli()))
            preparedStatement
        }, keyHolder)
        val orderId = keyHolder.keys!!["id"] as Long

        return Order(orderId, order.expenditure, order.provider, order.quantity, order.cost, order.transactionTime)
    }

    private fun fillOrder(
        order: OrderRecord
    ): Order {
        return Order(order.id, expendituresDAO.read(order.expenditureId), providerDAO.read(order.providerId),
            order.quantity, order.cost, Instant.ofEpochMilli(order.transactionTimestamp.time))
    }

    fun readAll(): List<Order> {
        val orderRecords = jdbcTemplate.query("select * from orders", OrderRecordMapper())
        return orderRecords.stream()
            .map(this::fillOrder)
            .collect(Collectors.toList())
    }

    fun delete(orderId: Long) {
        jdbcTemplate.update("delete from orders where id=?", orderId)
    }
}