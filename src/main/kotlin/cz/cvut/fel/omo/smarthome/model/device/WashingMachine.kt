package cz.cvut.fel.omo.smarthome.model.device

import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.messaging.Message
import cz.cvut.fel.omo.smarthome.model.messaging.MessageType

class WashingMachine(
    consumption: Consumption
) : Device(consumption) {

    private var processingStep = 0

    override fun run() {
        if(processingStep > 0) {
            processingStep--
            if(processingStep == 0) {
                stateMachine.changeState(DeviceStateCode.IDLE)
                sendMessage(messageFactory.createDeviceMessage(this, MessageType.WASHING_FINISHED))
            }
        }
    }

    override fun receiveMessage(message: Message) {
    }

    fun wash() {
        processingStep = 30
        stateMachine.changeState(DeviceStateCode.PROCESSING)
    }

    fun rinse() {
        processingStep = 10
        stateMachine.changeState(DeviceStateCode.PROCESSING)
    }
}