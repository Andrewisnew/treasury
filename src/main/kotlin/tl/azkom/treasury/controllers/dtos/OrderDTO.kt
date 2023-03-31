package tl.azkom.treasury.controllers.dtos

data class OrderDTO(val id: Long = 0, val expenditure: Long = 0, val provider: Long = 0, val quantity: Double = 0.0, val cost: Double = 0.0,
                    val transactionTime: String = "")
