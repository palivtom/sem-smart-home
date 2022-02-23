package cz.cvut.fel.omo.smarthome.cli.commands

import cz.cvut.fel.omo.smarthome.cli.exception.CommandParsingException
import cz.cvut.fel.omo.smarthome.export.JSONImporter
import cz.cvut.fel.omo.smarthome.model.building.House
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ImportCommand : Command(), KoinComponent {
    private val jsonImporter: JSONImporter by inject()

    private var path: String = ""

    override fun validateParams() {
        val hasPath = params.keys.stream().anyMatch{
            it == "-path"
        }

        if (!hasPath) throw CommandParsingException("Required parameter -path not supplied.")
    }

    override fun execute() {
        println("Importing JSON")
        val floors = jsonImporter.import(path)
        House.import(floors)
    }

    override fun parse(line: String) {
        val parts = line.split(" ")
        parseParams(parts)
        validateParams()
        validateAllowed()
        path = params["-path"]!!
    }

    private fun validateAllowed() {
        if (House.getInstance().floors.isNotEmpty()) throw CommandParsingException("Unable to import to already set up House.")
    }
}