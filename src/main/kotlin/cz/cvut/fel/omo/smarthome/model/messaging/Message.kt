package cz.cvut.fel.omo.smarthome.model.messaging

data class Message(
    val source: MessageSource,
    val type: MessageType
)