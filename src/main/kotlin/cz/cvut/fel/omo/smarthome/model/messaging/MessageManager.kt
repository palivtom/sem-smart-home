package cz.cvut.fel.omo.smarthome.model.messaging

import cz.cvut.fel.omo.smarthome.export.ExportVisitor
import cz.cvut.fel.omo.smarthome.logger.LogSeverity
import cz.cvut.fel.omo.smarthome.logger.Logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class MessageManager : IMessagable, KoinComponent {
    private val logger: Logger by inject()
    private val messagers: MutableList<IReceiver> = mutableListOf()
    private val messageHistory: Queue<Message> = ArrayDeque()

    override fun register(messager: IReceiver) {
        messagers.add(messager)
    }

    override fun unregister(messager: IReceiver) {
        messagers.remove(messager)
    }

    override fun sendMessage(message: Message) {
        messageHistory.add(message)
        logger.log(LogSeverity.INFO, "${message.source.sourceType} with id ${message.source.id} sent ${message.type} message")
        messagers.forEach{
            it.receiveMessage(message)
        }
    }

    fun getMessageHistory() : Queue<Message> {
        // May seem dumb, but we wanted to make messageHistory strictly immutable, hence the copy.
        return ArrayDeque(messageHistory)
    }

    fun accept(exporter: ExportVisitor) {
        exporter.export(this)
    }

    fun clear() {
        messageHistory.clear()
    }

}