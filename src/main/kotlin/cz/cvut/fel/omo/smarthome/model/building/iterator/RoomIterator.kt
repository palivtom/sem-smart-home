package cz.cvut.fel.omo.smarthome.model.building.iterator

import cz.cvut.fel.omo.smarthome.model.building.Floor
import cz.cvut.fel.omo.smarthome.model.building.Room
import java.util.NoSuchElementException

class RoomIterator(
    private val floors: MutableList<Floor>
) : Iterator<Room> {
    private var floorIndex = 0
    private var currentCycleRoomIndex = 0
    private var isEnd: Boolean

    init {
        isEnd = !findFirstPossible()
    }

    override fun hasNext() : Boolean {
        return floorIndex < floors.size && currentCycleRoomIndex < floors[floorIndex].rooms.size && !isEnd
    }

    override fun next(): Room {
        if (hasNext()) {
            val returnValue = floors[floorIndex].rooms[currentCycleRoomIndex]
            isEnd = !setNextIndexes()
            return returnValue
        } else {
            throw NoSuchElementException()
        }
    }

    private fun findFirstPossible() : Boolean {
        while (floorIndex < floors.size) {
            if (currentCycleRoomIndex < floors[floorIndex].rooms.size) {
                return true
            } else {
                floorIndex++
                currentCycleRoomIndex = 0
            }
        }
        return false
    }

    private fun setNextIndexes() : Boolean {
        currentCycleRoomIndex++
        return findFirstPossible()
    }
}