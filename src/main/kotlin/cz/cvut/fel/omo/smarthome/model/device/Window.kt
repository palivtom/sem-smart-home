package cz.cvut.fel.omo.smarthome.model.device

import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.messaging.Message
import cz.cvut.fel.omo.smarthome.model.messaging.MessageType
import kotlin.random.Random

class Window(
    consumption: Consumption
) : Device(consumption) {
    private var opened = false

    fun open() {
        if(!opened) {
            opened = true
            sendMessage(messageFactory.createDeviceMessage(this, MessageType.WINDOWS_OPENED))
        }
    }

    fun close() {
        if(opened) {
            opened = false
            sendMessage(messageFactory.createDeviceMessage(this, MessageType.WINDOWS_CLOSED))
        }
    }

    override fun run() {
        if(Random.nextInt(0, 50) == 5) {
            stateMachine.changeState(DeviceStateCode.BROKEN)
            sendMessage(messageFactory.createDeviceMessage(this, MessageType.DEVICE_BROKE))
        }
    }

    override fun receiveMessage(message: Message) {
        if(message.type == MessageType.WEATHER_RAINING || message.type == MessageType.SPRINKLERS_ON)
            close()
    }
}