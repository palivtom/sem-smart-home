package cz.cvut.fel.omo.smarthome.model.device

import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.messaging.Message
import cz.cvut.fel.omo.smarthome.model.messaging.MessageType
import kotlin.random.Random

class Door(
    consumption: Consumption
) : Device(consumption) {
    override fun run() {
        if (Random.nextInt(0, 50) == 5) {
            stateMachine.changeState(DeviceStateCode.BROKEN)
            sendMessage(messageFactory.createDeviceMessage(this, MessageType.DEVICE_BROKE))
        }
    }

    override fun receiveMessage(message: Message) {}

    fun close() {
        sendMessage(messageFactory.createDeviceMessage(this, MessageType.DOORS_CLOSED))
    }

    fun open() {
        sendMessage(messageFactory.createDeviceMessage(this, MessageType.DOORS_OPENED))
    }
}