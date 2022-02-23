package cz.cvut.fel.omo.smarthome.simulation

import cz.cvut.fel.omo.smarthome.logger.LogSeverity
import cz.cvut.fel.omo.smarthome.logger.Logger
import cz.cvut.fel.omo.smarthome.model.building.House
import cz.cvut.fel.omo.smarthome.model.building.Room
import cz.cvut.fel.omo.smarthome.model.device.Device
import cz.cvut.fel.omo.smarthome.model.item.Item
import cz.cvut.fel.omo.smarthome.model.livingbeing.LivingBeing
import cz.cvut.fel.omo.smarthome.model.messaging.MessageManager
import cz.cvut.fel.omo.smarthome.utils.LongAutoIncrement
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Simulation(
    private val house: House
) : KoinComponent {
    var id: Long = autoIncrement.value
    private val logger: Logger by inject()
    private val messageManager:MessageManager by inject()

    fun runSimulation(numberOfSteps: Int) {
        logger.clear()
        messageManager.clear()

        for (i in 0 until numberOfSteps) {
            logger.log(LogSeverity.INFO, "Running simulation step $i of $numberOfSteps")
            house.getDeviceIterator().forEach { it.run() }
            house.getLivingBeingIterator().forEach { it.run() }
            house.getItemIterator()
            house.electricityMeter.run()
        }
    }

    companion object {
        private val autoIncrement: LongAutoIncrement = LongAutoIncrement()

        inline fun <reified T : Device>findDeviceByType(lb: LivingBeing) : DeviceSearchResult<T>? {
            val it = House.getInstance().getRoomIterator()
            var searchedDevice : T? = null
            var currentRoom : Room? = null
            var targetRoom : Room? = null

            while(it.hasNext()) {
                val room = it.next()
                for(device in room.devices) {
                    if(device is T) {
                        targetRoom = room
                        searchedDevice = device
                    }
                }

                for(livingBeing in room.livingBeings) {
                    if (livingBeing == lb) {
                        currentRoom = room
                    }
                }
            }

            if(searchedDevice != null && currentRoom != null && targetRoom != null) {
                return DeviceSearchResult(searchedDevice, currentRoom, targetRoom)
            }

            return null
        }

        inline fun <reified T : Item>findItemByType(lb: LivingBeing) : ItemSearchResult<T>? {
            val it = House.getInstance().getRoomIterator()
            var searchedItem : T? = null
            var currentRoom : Room? = null
            var targetRoom : Room? = null

            while(it.hasNext()) {
                val room = it.next()
                for(item in room.devices) {
                    if(item is T) {
                        targetRoom = room
                        searchedItem = item
                    }
                }

                for(livingBeing in room.livingBeings) {
                    if (livingBeing == lb) {
                        currentRoom = room
                    }
                }
            }

            if(searchedItem != null && currentRoom != null && targetRoom != null) {
                return ItemSearchResult(searchedItem, currentRoom, targetRoom)
            }

            return null
        }

        fun findDeviceById(id: Long, lb: LivingBeing) : DeviceSearchResult<Device>? {
            val it = House.getInstance().getRoomIterator()
            var searchedDevice : Device? = null
            var currentRoom : Room? = null
            var targetRoom : Room? = null

            while(it.hasNext()) {
                val room = it.next()
                for(device in room.devices) {
                    if(device.id == id) {
                        targetRoom = room
                        searchedDevice = device
                    }
                }

                for(livingBeing in room.livingBeings) {
                    if (livingBeing == lb) {
                        currentRoom = room
                    }
                }
            }

            if(searchedDevice != null && currentRoom != null && targetRoom != null) {
                return DeviceSearchResult(searchedDevice, currentRoom, targetRoom)
            }


            return null
        }
    }
}