package cz.cvut.fel.omo.smarthome.model.device.state.machine.state

import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode

interface IDeviceState {
    fun getConsumption() : Double
    fun getStateCode(): DeviceStateCode
}