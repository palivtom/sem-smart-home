package cz.cvut.fel.omo.smarthome.simulation

import cz.cvut.fel.omo.smarthome.model.building.Room

data class DeviceSearchResult<T>(
    val device: T,
    val currRoom: Room,
    val targetRoom: Room
)
