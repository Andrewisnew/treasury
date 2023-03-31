package tl.azkom.treasury.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tl.azkom.treasury.dao.OrdersDAO
import tl.azkom.treasury.entities.Order

@Service
class OrdersService(@Autowired val ordersDAO: OrdersDAO) {
    fun fetchAllOrders() = ordersDAO.readAll()
    fun createOrder(order : Order) = ordersDAO.create(order)
    fun deleteOrder(orderId : Long) = ordersDAO.delete(orderId)
}
