package cz.cvut.fel.omo.smarthome.model.device.state.machine.state

import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode

class Processing(
    consumption: Double
) : DeviceState(
    consumption = consumption
){
    override fun getStateCode(): DeviceStateCode {
        return DeviceStateCode.PROCESSING
    }
}