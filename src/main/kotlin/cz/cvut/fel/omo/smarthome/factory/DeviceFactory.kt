package cz.cvut.fel.omo.smarthome.factory

import cz.cvut.fel.omo.smarthome.model.device.*
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption

class DeviceFactory {
    fun createDoor(consumption: Consumption) : Door {
        return Door(consumption)
    }

    fun createWindow(consumption: Consumption) : Window {
        return Window(consumption)
    }

    fun createSmartPhone(consumption: Consumption) : SmartPhone {
        return SmartPhone(consumption)
    }

    fun createGardenSprinkler(consumption: Consumption) : GardenSprinkler {
        return GardenSprinkler(consumption)
    }

    fun createWashingMachine(consumption: Consumption) : WashingMachine {
        return WashingMachine(consumption)
    }

    fun createWeatherStation(consumption: Consumption) : WeatherStation {
        return WeatherStation(consumption)
    }

    fun createKettle(consumption: Consumption): Kettle {
        return Kettle(consumption)
    }

    fun createElectricToothbrush(consumption: Consumption) : ElectricToothbrush {
        return ElectricToothbrush(consumption)
    }
}