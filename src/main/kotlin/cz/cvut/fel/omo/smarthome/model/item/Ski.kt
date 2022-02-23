package cz.cvut.fel.omo.smarthome.model.item

class Ski : Item() {
    private var dropped = false

    fun dropSki(): Boolean {
        if (!dropped) {
            dropped = true
            return true
        }
        return false
    }

    override fun run() {
        // todo
    }
}