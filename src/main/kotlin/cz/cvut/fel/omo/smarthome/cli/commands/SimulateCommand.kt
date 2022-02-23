package cz.cvut.fel.omo.smarthome.cli.commands

import cz.cvut.fel.omo.smarthome.cli.exception.CommandParsingException
import cz.cvut.fel.omo.smarthome.model.building.House
import cz.cvut.fel.omo.smarthome.simulation.Simulation
import org.koin.core.component.KoinComponent

class SimulateCommand : Command(), KoinComponent {
    private var numberOfSteps: Int = 100

    override fun execute() {
        val house = House.getInstance()
        val simulation = Simulation(house)

        println("Running simulation with id: ${simulation.id}")
        simulation.runSimulation(numberOfSteps)
    }

    override fun parse(line: String) {
        val parts = line.split(" ")

        parseParams(parts)
        validateParams()

        numberOfSteps = params["-n"]!!.toInt()
    }

    override fun validateParams() {
        if (params.containsKey("-n")) {
            validateIntInRange(params["-n"]!!, 1, Int.MAX_VALUE)
        } else {
            throw CommandParsingException("Required parameter -n not set.")
        }
    }

    private fun validateIntInRange(value: String, min: Int, max: Int) {
        value.toIntOrNull()?.apply {
            if (this in min..max) return
        }
        throw CommandParsingException("Required parameter -path not set.")
    }
}