package cz.cvut.fel.omo.smarthome.export

import cz.cvut.fel.omo.smarthome.logger.Logger
import cz.cvut.fel.omo.smarthome.model.building.House
import cz.cvut.fel.omo.smarthome.model.device.ElectricityMeter
import cz.cvut.fel.omo.smarthome.model.messaging.MessageManager

interface ExportVisitor {
    fun export(house: House)
    fun export(messageManager: MessageManager)
    fun export(logger: Logger)
    fun export(electricityMeter: ElectricityMeter)
}