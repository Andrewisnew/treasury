package tl.azkom.treasury.exceptions

class NotFoundException(override val message: String?) : Exception(message) {
}