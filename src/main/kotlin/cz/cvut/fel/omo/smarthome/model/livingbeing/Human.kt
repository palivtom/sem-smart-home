package cz.cvut.fel.omo.smarthome.model.livingbeing

import cz.cvut.fel.omo.smarthome.model.device.SmartPhone
import cz.cvut.fel.omo.smarthome.model.device.WashingMachine
import cz.cvut.fel.omo.smarthome.model.messaging.MessageType
import cz.cvut.fel.omo.smarthome.simulation.Simulation
import kotlin.random.Random

class Human : LivingBeing() {

    private fun washClothes() {
        val searchResult = Simulation.findDeviceByType<WashingMachine>(this)

        if(searchResult != null) {
            searchResult.currRoom.leave(this)
            searchResult.targetRoom.enter(this)
            searchResult.device.wash()
        }
    }

    private fun checkMessages() {
        val searchResult = Simulation.findDeviceByType<SmartPhone>(this)

        if(searchResult != null) {
            searchResult.currRoom.leave(this)
            searchResult.targetRoom.enter(this)
            val messages = searchResult.device.check()
            for(message in messages) {
                if(message.type == MessageType.DEVICE_BROKE) {
                    val brokenDeviceResult = Simulation.findDeviceById(message.source.id, this)
                    if(brokenDeviceResult != null && brokenDeviceResult.device.isBroken()) {
                        brokenDeviceResult.currRoom.leave(this)
                        brokenDeviceResult.targetRoom.enter(this)
                        brokenDeviceResult.device.getManual()
                        brokenDeviceResult.device.repair(this)
                    }
                }
            }
        }
    }

    override fun run() {
        if(Random.nextInt(0, 10) == 0) {
            washClothes()
        }

        if(Random.nextInt(0, 1) == 0) {
            checkMessages()
        }
    }
}