package cz.cvut.fel.omo.smarthome.cli.commands

import cz.cvut.fel.omo.smarthome.cli.exception.CommandParsingException
import cz.cvut.fel.omo.smarthome.export.JSONExporter
import cz.cvut.fel.omo.smarthome.export.XMLExporter
import cz.cvut.fel.omo.smarthome.logger.Logger
import cz.cvut.fel.omo.smarthome.model.building.House
import cz.cvut.fel.omo.smarthome.model.messaging.MessageManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.Exception

class ExportCommand : Command(), KoinComponent {
    private val jsonExporter: JSONExporter by inject()
    private val xmlExporter: XMLExporter by inject()
    private val messageManager: MessageManager by inject()
    private val logger: Logger by inject()

    companion object {
        private val VALID_PARAMS: MutableList<String> = mutableListOf("-config", "-message", "-consumption", "-log")
    }

    var path: String = ""
    var type: String = ""

    override fun execute() {
        val house = House.getInstance()
        jsonExporter.path = path
        xmlExporter.path = path

        try {
            when (params.getOrDefault("-type", "JSON")) {
                "JSON" -> {
                    if (params.keys.contains("-config")) {
                        println("Exporting house config into JSON")
                        house.accept(jsonExporter)
                    }
                    else if (params.keys.contains("-message")) {
                        println("Exporting message stack into JSON")
                        messageManager.accept(jsonExporter)
                    }
                    else if(params.keys.contains("-log")) {
                        println("Exporting message log into JSON")
                        logger.accept(jsonExporter)
                    }
                    else if (params.keys.contains("-consumption")) {
                        println("Exporting consumption data into JSON")
                        house.electricityMeter.accept(jsonExporter)
                    }
                }
                "XML" -> {
                    println("Operation not available.")
                }
            }
        }
        catch (e: Exception) {
            println("An exception occurred while exporting your file.")
            throw e
        }
    }

    override fun parseParams(parts: List<String>) {
        for (part in parts) {
            val keyValue = part.split("=")
            when (keyValue.size) {
                1 -> params[keyValue[0]] = ""
                2 -> params[keyValue[0]] = keyValue[1]
                else -> throw CommandParsingException("Weirdly formatted param detected. Maybe a typo?")
            }
        }
    }

    override fun parse(line: String) {
        val parts = line.split(" ")
        parseParams(parts)
        validateParams()
        path = params["-path"]!!
    }

    override fun validateParams() {
        val hasPath = params.keys.stream().anyMatch{
            it == "-path"
        }
        if (!hasPath) throw CommandParsingException("Required parameter -path not supplied.")

        var typeFound = false
        params.entries.forEach{
            if (VALID_PARAMS.contains(it.key) && it.value == "") {
                val otherTypes = VALID_PARAMS.toMutableList()
                otherTypes.remove(it.key)
                typeFound = true
                params.entries.forEach{ it2 ->
                    if (otherTypes.contains(it2.key)) throw CommandParsingException("Only one type is allowed.")
                }
            }
        }
        if (!typeFound) throw CommandParsingException("Type was not given.")
    }
}