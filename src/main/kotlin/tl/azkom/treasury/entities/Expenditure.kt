package tl.azkom.treasury.entities

data class Expenditure(val id: Long, val name: String, val quantity: Double?, val unit: ExpenditureUnit, val tags: List<Tag>) {
    constructor(id: Long, name: String, unit: ExpenditureUnit, tags: List<Tag>) : this(id, name, null, unit, tags)
}