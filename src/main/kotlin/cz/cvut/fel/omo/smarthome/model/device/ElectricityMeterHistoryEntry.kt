package cz.cvut.fel.omo.smarthome.model.device

import java.util.*

data class ElectricityMeterHistoryEntry (
    val date: Date,
    val consumption: Double
)