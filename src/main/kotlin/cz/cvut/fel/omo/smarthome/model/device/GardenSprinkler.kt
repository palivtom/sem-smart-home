package cz.cvut.fel.omo.smarthome.model.device

import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.messaging.Message
import cz.cvut.fel.omo.smarthome.model.messaging.MessageType

// TODO: Receive the message about the rain and shut down based on it
class GardenSprinkler(consumption: Consumption) : Device(consumption) {
    private var processingStep: Int = 0

    override fun run() {
        if(processingStep > 0) {
            processingStep--
            if(processingStep == 0)
                turnOff()
        }
    }

    override fun receiveMessage(message: Message) {
        if(message.type == MessageType.WEATHER_RAINING && processingStep > 0) {
            turnOff()
        }
    }

    fun turnOn() {
        // NOTE: infinite on purpose
        processingStep = -1
        stateMachine.changeState(DeviceStateCode.PROCESSING)
        sendMessage(messageFactory.createDeviceMessage(this, MessageType.SPRINKLERS_ON))
    }

    fun timer() {
        processingStep = 10
        stateMachine.changeState(DeviceStateCode.PROCESSING)
        sendMessage(messageFactory.createDeviceMessage(this, MessageType.SPRINKLERS_ON))
    }

    fun turnOff() {
        processingStep = 0
        stateMachine.changeState(DeviceStateCode.IDLE)
        sendMessage(messageFactory.createDeviceMessage(this, MessageType.SPRINKLERS_OFF))
    }
}