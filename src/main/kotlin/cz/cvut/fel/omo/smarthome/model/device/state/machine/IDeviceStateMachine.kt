package cz.cvut.fel.omo.smarthome.model.device.state.machine

import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode

interface IDeviceStateMachine {
    fun changeState(stateCode: DeviceStateCode)
    fun getConsumption() : Double
    fun getStateCode() : DeviceStateCode
}