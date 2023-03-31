package tl.azkom.treasury.entities

data class Tag(val id: Long, val name: String) {
    fun hasId(): Boolean = id != 0L
}