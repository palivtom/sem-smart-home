package cz.cvut.fel.omo.smarthome.model.messaging

import cz.cvut.fel.omo.smarthome.model.device.Device

class MessageFactory {
    fun createDeviceMessage(source: Device, messageType: MessageType): Message {
        return Message(MessageSource(source.id, SourceType.DEVICE), messageType)
    }
}