package cz.cvut.fel.omo.smarthome.model.device

import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.messaging.Message
import cz.cvut.fel.omo.smarthome.model.messaging.MessageType
import java.util.*

class SmartPhone(
    consumption: Consumption
) : Device(consumption) {
    private var messages: Queue<Message> = ArrayDeque()

    override fun run() {
    }

    override fun receiveMessage(message: Message) {
        if(message.type == MessageType.DEVICE_BROKE)
            messages.add(message)
    }

    fun check() : Queue<Message> {
        val rv = ArrayDeque(messages)
        messages.clear()
        return rv
    }
}