package cz.cvut.fel.omo.smarthome.model.device

import cz.cvut.fel.omo.smarthome.model.device.state.machine.DeviceStateMachine
import cz.cvut.fel.omo.smarthome.model.device.state.machine.IDeviceStateMachine
import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.livingbeing.Human
import cz.cvut.fel.omo.smarthome.model.messaging.*
import cz.cvut.fel.omo.smarthome.simulation.ISimulatable
import cz.cvut.fel.omo.smarthome.utils.LongAutoIncrement
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class Device(
    private var consumption: Consumption
) : KoinComponent, IDevice, ISimulatable, IReceiver {
    val messagingManager: MessageManager? by inject<MessageManager>().apply {
        this.value.register(this@Device)
    }
    val messageFactory: MessageFactory by inject()

    companion object {
        val longAutoIncrement: LongAutoIncrement = LongAutoIncrement()
    }

    var id: Long = longAutoIncrement.value
    protected val stateMachine: IDeviceStateMachine = DeviceStateMachine(DeviceStateCode.UP, consumption)
    val currentConsumption : Double
        get() = stateMachine.getConsumption()

    protected fun sendMessage(message: Message) {
        messagingManager?.sendMessage(message)
    }

    fun damage() : Boolean {
        if(stateMachine.getStateCode() != DeviceStateCode.BROKEN) {
            stateMachine.changeState(DeviceStateCode.BROKEN)
            sendMessage(messageFactory.createDeviceMessage(this, MessageType.DEVICE_BROKE))
            return true
        }

        return false
    }

    fun repair(human: Human) {
        stateMachine.changeState(DeviceStateCode.UP)
    }

    fun getManual() {
        // Yeet
    }

    fun isBroken() : Boolean {
        return stateMachine.getStateCode() == DeviceStateCode.BROKEN
    }

    fun getExportConsumption() : Consumption {
        return consumption
    }
}