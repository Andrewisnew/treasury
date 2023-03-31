package tl.azkom.treasury.entities

import java.time.Instant

data class Order(val id: Long, val expenditure: Expenditure, val provider: Provider, val quantity: Double, val cost: Double, val transactionTime: Instant)