package cz.cvut.fel.omo.smarthome.model.building

import cz.cvut.fel.omo.smarthome.exception.SmartHomeException
import cz.cvut.fel.omo.smarthome.model.device.Device
import cz.cvut.fel.omo.smarthome.model.item.Item
import cz.cvut.fel.omo.smarthome.model.livingbeing.LivingBeing
import cz.cvut.fel.omo.smarthome.utils.LongAutoIncrement

class Room (
    var name: String = ""
) {
    companion object {
        val longAutoIncrement: LongAutoIncrement = LongAutoIncrement()
    }

    var id: Long = longAutoIncrement.value
    var devices: MutableList<Device> = mutableListOf()
    var livingBeings: MutableList<LivingBeing> = mutableListOf()
    var items: MutableList<Item> = mutableListOf()

    fun enter(lb: LivingBeing) {
        livingBeings.add(lb)
        return
    }

    fun leave(lb: LivingBeing) {
        val leavingOne = livingBeings.find { it == lb } ?: throw SmartHomeException("Living being is not in the room.")
        livingBeings.remove(leavingOne)
    }

    fun addDevice(device: Device): Boolean {
        devices.add(device)
        return true
    }

    fun addItem(item: Item): Boolean {
        items.add(item)
        return true
    }
}