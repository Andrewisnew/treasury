package tl.azkom.treasury.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import tl.azkom.treasury.controllers.dtos.OrderDTO
import tl.azkom.treasury.entities.Order
import tl.azkom.treasury.services.ExpendituresService
import tl.azkom.treasury.services.OrdersService
import tl.azkom.treasury.services.ProviderService
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME


@Controller
class OrdersController(
    @Autowired val ordersService: OrdersService,
    @Autowired val providerService: ProviderService,
    @Autowired val expendituresService: ExpendituresService
) {
    @GetMapping("/orders")
    fun getOrders(
        model: Model
    ): String? {
        val allOrders = this.ordersService.fetchAllOrders()
        val allExpenditures = this.expendituresService.fetchAllExpenditures()
        val allProviders = this.providerService.fetchAllProviders()
        model.addAttribute("order", OrderDTO())
        model.addAttribute("orders", allOrders)
        model.addAttribute("expenditures", allExpenditures)
        model.addAttribute("providers", allProviders)
        return "orders"
    }

    @PostMapping("/orders/create")
    fun post(
        @ModelAttribute order: OrderDTO,
        model: Model
    ): String? {
        val localDateTime: LocalDateTime = LocalDateTime.parse(order.transactionTime, ISO_LOCAL_DATE_TIME)
        val zoneId = ZoneId.systemDefault()
        val zonedDateTime: ZonedDateTime = localDateTime.atZone(zoneId)
        val instant = zonedDateTime.toInstant()
        val provider = this.providerService.fetchAllProviders().stream()
            .filter { t -> order.provider == t.id }
            .findAny()
            .orElseThrow()
        val expenditure = this.expendituresService.fetchAllExpenditures().stream()
            .filter { t -> order.expenditure == t.id }
            .findAny()
            .orElseThrow()

        ordersService.createOrder(
            Order(
                order.id, expenditure, provider, order.quantity, order.cost, instant
            )
        )
        return "redirect:/orders"
    }

    @GetMapping("/orders/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model
    ): String? {
        ordersService.deleteOrder(id)
        return "redirect:/orders"
    }
}