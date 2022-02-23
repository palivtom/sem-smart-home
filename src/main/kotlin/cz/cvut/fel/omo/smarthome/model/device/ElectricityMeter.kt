package cz.cvut.fel.omo.smarthome.model.device

import cz.cvut.fel.omo.smarthome.export.ExportVisitor
import cz.cvut.fel.omo.smarthome.model.building.House
import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.messaging.Message
import java.util.*

class ElectricityMeter(
    consumption: Consumption
) : Device(consumption) {
    private val history : MutableList<ElectricityMeterHistoryEntry> = mutableListOf()

    private fun measureConsumption() {
        stateMachine.changeState(DeviceStateCode.PROCESSING)
        val it = House.getInstance().getDeviceIterator()
        var totalConsumption = 0.0
        while(it.hasNext()) {
            val device = it.next()
            totalConsumption += device.currentConsumption
        }

        history.add(ElectricityMeterHistoryEntry(Date(), totalConsumption))
        stateMachine.changeState(DeviceStateCode.IDLE)
    }

    override fun run() {
        measureConsumption()
    }

    override fun receiveMessage(message: Message) {

    }

    fun accept(visitor: ExportVisitor) {
        visitor.export(this)
    }

    fun getHistory() : MutableList<ElectricityMeterHistoryEntry> {
        return history.toMutableList()
    }
}