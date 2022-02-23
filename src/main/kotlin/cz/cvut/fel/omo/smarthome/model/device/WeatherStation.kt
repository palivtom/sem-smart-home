package cz.cvut.fel.omo.smarthome.model.device

import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.messaging.Message
import cz.cvut.fel.omo.smarthome.model.messaging.MessageType
import kotlin.random.Random

// TODO: Weather station will send message about rain, the garden sprinklers will shut down
class WeatherStation(consumption: Consumption) : Device(consumption) {
    override fun run() {
        if(Random.nextInt(0, 20) == 5)
            sendMessage(messageFactory.createDeviceMessage(this, MessageType.WEATHER_RAINING))

        if(Random.nextInt(0, 10) == 8)
            sendMessage(messageFactory.createDeviceMessage(this, MessageType.WEATHER_OTHER))

        if(Random.nextInt(0,50) == 5) {
            stateMachine.changeState(DeviceStateCode.BROKEN)
            sendMessage(messageFactory.createDeviceMessage(this, MessageType.DEVICE_BROKE))
        }
    }

    override fun receiveMessage(message: Message) {}
}