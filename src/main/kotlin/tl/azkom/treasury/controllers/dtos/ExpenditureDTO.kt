package tl.azkom.treasury.controllers.dtos

import tl.azkom.treasury.entities.ExpenditureUnit

data class ExpenditureDTO(val id: Long = 0, val name: String = "", val quantity: Double? = null, val unit: ExpenditureUnit? = null, val tags: List<Long> = emptyList())
