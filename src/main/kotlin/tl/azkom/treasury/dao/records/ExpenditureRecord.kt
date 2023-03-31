package tl.azkom.treasury.dao.records

import tl.azkom.treasury.entities.ExpenditureUnit

data class ExpenditureRecord(val id: Long, val name: String, val quantity: Double?, val unit: ExpenditureUnit)