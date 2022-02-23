package cz.cvut.fel.omo.smarthome.model.device.state.machine.state

import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode

class Broken(
    consumption: Double
) : DeviceState(
    consumption = consumption
){
    override fun getStateCode(): DeviceStateCode {
        return DeviceStateCode.BROKEN
    }
}