package cz.cvut.fel.omo.smarthome.model.building

import cz.cvut.fel.omo.smarthome.utils.LongAutoIncrement

class Floor (
    var name: String = ""
) {
    companion object {
        val longAutoIncrement: LongAutoIncrement = LongAutoIncrement()
    }

    var id: Long = longAutoIncrement.value
    var rooms: MutableList<Room> = mutableListOf()

    fun addRoom(room: Room): Boolean {
        rooms.add(room)
        return true
    }
}