package cz.cvut.fel.omo.smarthome.utils

class LongAutoIncrement(
    value: Long = 0,
    private val increment: Long = 1
) {
    var value: Long = value
        get() {
            val tmp = field
            field += increment
            return tmp
        }

    fun peekValue(): Long {
        return value
    }
}
