package tl.azkom.treasury.dao.records

import java.sql.Timestamp

data class OrderRecord(val id: Long, val expenditureId: Long, val providerId: Long, val quantity: Double, val cost: Double, val transactionTimestamp: Timestamp)