package cz.cvut.fel.omo.smarthome.model.messaging

interface IMessagable {
    fun register(messager: IReceiver)
    fun unregister(messager: IReceiver)
    fun sendMessage(message: Message)
}