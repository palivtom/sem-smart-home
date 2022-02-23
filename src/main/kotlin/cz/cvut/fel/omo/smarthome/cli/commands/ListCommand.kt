package cz.cvut.fel.omo.smarthome.cli.commands

import cz.cvut.fel.omo.smarthome.cli.exception.CommandParsingException
import cz.cvut.fel.omo.smarthome.model.building.House

class ListCommand : Command() {
    companion object {
        private val VALID_PARAMS: MutableList<String> = mutableListOf("floor", "room", "device", "livingBeing", "item")
    }
    private var what: String = ""

    override fun execute() {
        val house = House.getInstance()
        when (what) {
            "floor" -> listFloors(house)
            "room" -> listRooms(house)
            "device" -> listDevices(house)
            "livingBeing" -> listLivingBeings(house)
            "item" -> listItems(house)
        }
    }

    private fun listItems(house: House) {
        println("Item list")
        println("ID \t NAME")
        house.getItemIterator().forEach {
            println("${it.id} \t ${it.javaClass}")
        }
    }

    private fun listLivingBeings(house: House) {
        println("Living Being list")
        println("ID \t NAME")
        house.getLivingBeingIterator().forEach {
            println("${it.id} \t ${it.javaClass}")
        }
    }

    private fun listDevices(house: House) {
        println("Device list")
        println("ID \t NAME")
        house.getDeviceIterator().forEach {
            println("${it.id} \t ${it.javaClass}")
        }
    }

    private fun listRooms(house: House) {
        println("Room list")
        println("ID \t NAME")
        house.getRoomIterator().forEach {
            println("${it.id} \t ${it.name}")
        }
    }

    private fun listFloors(house: House) {
        println("Floor list")
        println("ID \t NAME")
        house.floors.forEach {
            println("${it.id} \t ${it.name}")
        }
    }

    override fun parse(line: String) {
        val parts = line.split(" ")
        if (parts.size < 2) throw CommandParsingException("Command too short. Specify the <what>")

        what = parts[1]
        validateParams()
    }

    override fun validateParams() {
        if (!VALID_PARAMS.contains(what))
            throw CommandParsingException("Invalid paramater to list: $what")
    }
}