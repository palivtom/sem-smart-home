package cz.cvut.fel.omo.smarthome.simulation

import cz.cvut.fel.omo.smarthome.model.building.Room

data class ItemSearchResult<T>(
    val item: T,
    val currRoom: Room,
    val targetRoom: Room
)
