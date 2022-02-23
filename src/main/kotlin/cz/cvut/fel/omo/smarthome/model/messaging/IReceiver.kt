package cz.cvut.fel.omo.smarthome.model.messaging

interface IReceiver {
    fun receiveMessage(message: Message)
}