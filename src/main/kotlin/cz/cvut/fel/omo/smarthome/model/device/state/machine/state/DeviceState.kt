package cz.cvut.fel.omo.smarthome.model.device.state.machine.state

abstract class DeviceState(
    private val consumption: Double
) : IDeviceState {
    override fun getConsumption(): Double {
        return consumption
    }
}