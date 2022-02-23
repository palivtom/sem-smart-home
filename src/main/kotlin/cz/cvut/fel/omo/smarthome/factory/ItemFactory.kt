package cz.cvut.fel.omo.smarthome.factory

import cz.cvut.fel.omo.smarthome.model.item.Bicycle
import cz.cvut.fel.omo.smarthome.model.item.Ski

class ItemFactory {
    fun createSki() : Ski {
        return Ski()
    }
    fun createBicycle() : Bicycle {
        return Bicycle()
    }
}