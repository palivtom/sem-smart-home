package cz.cvut.fel.omo.smarthome.model.building

import cz.cvut.fel.omo.smarthome.exception.FloorNotFoundException
import cz.cvut.fel.omo.smarthome.exception.RoomNotFoundException
import cz.cvut.fel.omo.smarthome.export.ExportVisitor
import cz.cvut.fel.omo.smarthome.export.IExportable
import cz.cvut.fel.omo.smarthome.model.building.iterator.*
import cz.cvut.fel.omo.smarthome.model.device.Device
import cz.cvut.fel.omo.smarthome.model.device.ElectricityMeter
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.livingbeing.LivingBeing
import cz.cvut.fel.omo.smarthome.model.item.Item

class House private constructor() : HomeIterators, IExportable {
    var floors: MutableList<Floor> = mutableListOf()
    val electricityMeter: ElectricityMeter = ElectricityMeter(Consumption(1.0, 0.0, 0.0))

    companion object {
        @Volatile private var instance: House? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: House().also { instance = it }
            }

        fun import(floors: MutableList<Floor>) {
            instance = House()
            instance!!.floors = floors

            var highestFloorId : Long = 0
            var highestRoomId : Long = 0
            var highestDeviceId : Long = 0
            var highestLivingBeingId: Long = 0
            var highestItemId: Long = 0

            for(floor in floors) {
                if(floor.id > highestFloorId) highestFloorId = floor.id
                for(room in floor.rooms) {
                    if(room.id > highestRoomId) highestRoomId = room.id
                    for(device in room.devices) {
                        if(device.id > highestDeviceId) highestDeviceId = device.id
                    }

                    for(livingBeing in room.livingBeings) {
                        if(livingBeing.id > highestLivingBeingId) highestLivingBeingId = livingBeing.id
                    }

                    for(item in room.items) {
                        if(item.id > highestItemId) highestItemId = item.id
                    }
                }
            }

            Floor.longAutoIncrement.value = highestFloorId + 1
            Room.longAutoIncrement.value = highestRoomId + 1
            Device.longAutoIncrement.value = highestDeviceId + 1
            LivingBeing.longAutoIncrement.value = highestLivingBeingId + 1
            Item.longAutoIncrement.value = highestItemId + 1
        }
    }

    override fun accept(visitor: ExportVisitor) {
        visitor.export(this)
    }

    fun addFloor(floor: Floor) {
        floors.add(floor)
    }

    fun addRoom(room: Room, floorId: Long) {
        var done = false
        val floorIter = getFloorIterator()
        floorIter.forEach {
            if (it.id == floorId) {
                done = it.addRoom(room)
                return
            }
        }

        if (!done) throw FloorNotFoundException("Unable to add this room. Floor with id $floorId not found.")
    }

    fun addDevice(device: Device, roomId: Long) {
        var done = false
        val roomIter = getRoomIterator()
        roomIter.forEach {
            if (it.id == roomId) {
                done = it.addDevice(device)
                return
            }
        }

        if (!done) throw RoomNotFoundException("Unable to add this device. Room with id $roomId not found.")
    }

    fun addLivingBeing(livingBeing: LivingBeing, roomId: Long) {
        var done = false
        val roomIter = getRoomIterator()
        roomIter.forEach {
            if (it.id == roomId) {
                it.enter(livingBeing)
                done = true
                return
            }
        }

        if(!done) throw RoomNotFoundException("Unable to add this livingBeing. Room with id $roomId not found.")
    }

    fun addItem(item: Item, roomId: Long) {
        var done = false
        val roomIter = getRoomIterator()
        roomIter.forEach {
            if (it.id == roomId) {
                done = it.addItem(item)
                return
            }
        }

        if (!done) throw RoomNotFoundException("Unable to add this item. Room with id $roomId not found.")
    }

    override fun getFloorIterator(): Iterator<Floor> {
        return floors.iterator()
    }

    override fun getRoomIterator(): RoomIterator {
        return RoomIterator(floors)
    }

    override fun getDeviceIterator(): DeviceIterator {
        return DeviceIterator(floors)
    }

    override fun getLivingBeingIterator(): LivingBeingIterator {
        return LivingBeingIterator(floors)
    }

    override fun getItemIterator(): ItemIterator {
        return ItemIterator(floors)
    }
}