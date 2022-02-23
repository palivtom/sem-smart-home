package cz.cvut.fel.omo.smarthome.model.building.iterator

import cz.cvut.fel.omo.smarthome.model.building.Floor

interface HomeIterators {
    fun getFloorIterator(): Iterator<Floor>
    fun getRoomIterator(): RoomIterator
    fun getDeviceIterator(): DeviceIterator
    fun getLivingBeingIterator(): LivingBeingIterator
    fun getItemIterator(): ItemIterator
}