package cz.cvut.fel.omo.smarthome.model.device

import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.messaging.Message

class Kettle(consumption: Consumption) : Device(consumption) {
    override fun run() {
    }

    override fun receiveMessage(message: Message) {
    }
}