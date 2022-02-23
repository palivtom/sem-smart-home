package cz.cvut.fel.omo.smarthome.model.building.iterator

import cz.cvut.fel.omo.smarthome.model.building.Floor
import cz.cvut.fel.omo.smarthome.model.device.Device
import java.util.NoSuchElementException

class DeviceIterator(
    private val floors: MutableList<Floor>
) : Iterator<Device> {
    private var floorIndex = 0
    private var currentCycleRoomIndex = 0
    private var currentCycleDeviceIndex = 0
    private var isEnd: Boolean

    init {
        isEnd = !findFirstPossible()
    }

    override fun hasNext() : Boolean {
        return floorIndex < floors.size
                && currentCycleRoomIndex < floors[floorIndex].rooms.size
                && currentCycleDeviceIndex < floors[floorIndex].rooms[currentCycleRoomIndex].devices.size
                && !isEnd
    }

    override fun next(): Device {
        if (hasNext()) {
            val returnValue = floors[floorIndex].rooms[currentCycleRoomIndex].devices[currentCycleDeviceIndex]
            isEnd = !setNextIndexes()
            return returnValue
        } else {
            throw NoSuchElementException()
        }
    }

    private fun findFirstPossible() : Boolean {
        while (floorIndex < floors.size) {
           if (currentCycleRoomIndex < floors[floorIndex].rooms.size) {
                if (currentCycleDeviceIndex < floors[floorIndex].rooms[currentCycleRoomIndex].devices.size) {
                    return true
                } else {
                    currentCycleRoomIndex++
                    currentCycleDeviceIndex = 0
                }
            } else {
               floorIndex++
               currentCycleRoomIndex = 0
           }
        }
        return false
    }

    private fun setNextIndexes() : Boolean {
        currentCycleDeviceIndex++
        return findFirstPossible()
    }
}