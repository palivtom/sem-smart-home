package cz.cvut.fel.omo.smarthome.model.building.iterator

import cz.cvut.fel.omo.smarthome.model.building.Floor
import cz.cvut.fel.omo.smarthome.model.livingbeing.LivingBeing
import java.util.NoSuchElementException

class LivingBeingIterator(
    private val floors: MutableList<Floor>
) : Iterator<LivingBeing> {
    private var floorIndex = 0
    private var currentCycleRoomIndex = 0
    private var currentCycleLivingBeingIndex = 0
    private var isEnd: Boolean

    init {
        isEnd = !findFirstPossible()
    }

    override fun hasNext() : Boolean {
        return floorIndex < floors.size
                && currentCycleRoomIndex < floors[floorIndex].rooms.size
                && currentCycleLivingBeingIndex < floors[floorIndex].rooms[currentCycleRoomIndex].livingBeings.size
                && !isEnd
    }

    override fun next(): LivingBeing {
        if (hasNext()) {
            val returnValue = floors[floorIndex].rooms[currentCycleRoomIndex].livingBeings[currentCycleLivingBeingIndex]
            isEnd = !setNextIndexes()
            return returnValue
        } else {
            throw NoSuchElementException()
        }
    }

    private fun findFirstPossible() : Boolean {
        while (floorIndex < floors.size) {
           if (currentCycleRoomIndex < floors[floorIndex].rooms.size) {
                if (currentCycleLivingBeingIndex < floors[floorIndex].rooms[currentCycleRoomIndex].livingBeings.size) {
                    return true
                } else {
                    currentCycleRoomIndex++
                    currentCycleLivingBeingIndex = 0
                }
            } else {
               floorIndex++
               currentCycleRoomIndex = 0
           }
        }
        return false
    }

    private fun setNextIndexes() : Boolean {
        currentCycleLivingBeingIndex++
        return findFirstPossible()
    }
}