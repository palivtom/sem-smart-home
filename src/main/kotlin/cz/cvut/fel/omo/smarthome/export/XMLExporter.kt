package cz.cvut.fel.omo.smarthome.export

import cz.cvut.fel.omo.smarthome.logger.Logger
import cz.cvut.fel.omo.smarthome.model.building.House
import cz.cvut.fel.omo.smarthome.model.device.ElectricityMeter
import cz.cvut.fel.omo.smarthome.model.messaging.MessageManager

class XMLExporter : ExportVisitor {
    var path: String = ""
    override fun export(house: House) {
        TODO("Not yet implemented")
    }

    override fun export(messageManager: MessageManager) {
        TODO("Not yet implemented")
    }

    override fun export(logger: Logger) {
        TODO("Not yet implemented")
    }

    override fun export(electricityMeter: ElectricityMeter) {
        TODO("Not yet implemented")
    }
}