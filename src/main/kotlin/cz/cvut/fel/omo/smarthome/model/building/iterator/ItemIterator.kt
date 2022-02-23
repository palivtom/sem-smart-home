package cz.cvut.fel.omo.smarthome.model.building.iterator

import cz.cvut.fel.omo.smarthome.model.building.Floor
import cz.cvut.fel.omo.smarthome.model.item.Item
import java.util.NoSuchElementException

class ItemIterator(
    private val floors: MutableList<Floor>
) : Iterator<Item> {
    private var floorIndex = 0
    private var currentCycleRoomIndex = 0
    private var currentCycleItemIndex = 0
    private var isEnd: Boolean

    init {
        isEnd = !findFirstPossible()
    }

    override fun hasNext() : Boolean {
        return floorIndex < floors.size
                && currentCycleRoomIndex < floors[floorIndex].rooms.size
                && currentCycleItemIndex < floors[floorIndex].rooms[currentCycleRoomIndex].items.size
                && !isEnd
    }

    override fun next(): Item {
        if (hasNext()) {
            val returnValue = floors[floorIndex].rooms[currentCycleRoomIndex].items[currentCycleItemIndex]
            isEnd = !setNextIndexes()
            return returnValue
        } else {
            throw NoSuchElementException()
        }
    }

    private fun findFirstPossible() : Boolean {
        while (floorIndex < floors.size) {
           if (currentCycleRoomIndex < floors[floorIndex].rooms.size) {
                if (currentCycleItemIndex < floors[floorIndex].rooms[currentCycleRoomIndex].items.size) {
                    return true
                } else {
                    currentCycleRoomIndex++
                    currentCycleItemIndex = 0
                }
            } else {
               floorIndex++
               currentCycleRoomIndex = 0
           }
        }
        return false
    }

    private fun setNextIndexes() : Boolean {
        currentCycleItemIndex++
        return findFirstPossible()
    }
}