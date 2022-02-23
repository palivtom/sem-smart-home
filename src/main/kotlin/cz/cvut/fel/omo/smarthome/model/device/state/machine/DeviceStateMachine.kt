package cz.cvut.fel.omo.smarthome.model.device.state.machine

import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.device.state.machine.enum.DeviceStateCode
import cz.cvut.fel.omo.smarthome.model.device.state.machine.state.*

class DeviceStateMachine(
    stateCode: DeviceStateCode,
    consumption: Consumption
) : IDeviceStateMachine {

    private var currentState: IDeviceState
    private val stateMap: MutableMap<DeviceStateCode, IDeviceState>

    override fun changeState(stateCode: DeviceStateCode) {
        currentState = stateMap[stateCode]!!
    }

    override fun getConsumption(): Double {
        return currentState.getConsumption()
    }

    override fun getStateCode(): DeviceStateCode {
        return currentState.getStateCode()
    }

    init {
        stateMap = mutableMapOf(
            DeviceStateCode.UP to Up(consumption.up),
            DeviceStateCode.IDLE to Idle(consumption.idle),
            DeviceStateCode.PROCESSING to Processing(consumption.processing),
            DeviceStateCode.BROKEN to Broken(0.0),
        )
        currentState = stateMap[stateCode]!!
    }
}