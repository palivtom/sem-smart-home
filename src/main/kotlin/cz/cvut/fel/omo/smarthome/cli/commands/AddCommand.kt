package cz.cvut.fel.omo.smarthome.cli.commands

import cz.cvut.fel.omo.smarthome.cli.exception.CommandExecutionException
import cz.cvut.fel.omo.smarthome.cli.exception.CommandParsingException
import cz.cvut.fel.omo.smarthome.export.DeviceType
import cz.cvut.fel.omo.smarthome.export.ItemType
import cz.cvut.fel.omo.smarthome.export.LivingBeingType
import cz.cvut.fel.omo.smarthome.factory.DeviceFactory
import cz.cvut.fel.omo.smarthome.factory.ItemFactory
import cz.cvut.fel.omo.smarthome.factory.LivingBeingFactory
import cz.cvut.fel.omo.smarthome.model.building.Floor
import cz.cvut.fel.omo.smarthome.model.building.House
import cz.cvut.fel.omo.smarthome.model.building.Room
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.device.Device
import cz.cvut.fel.omo.smarthome.model.item.Item
import cz.cvut.fel.omo.smarthome.model.livingbeing.LivingBeing
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddCommand : Command(), KoinComponent {
    private var what = ""
    private val deviceFactory: DeviceFactory by inject()
    private val livingBeingFactory: LivingBeingFactory by inject()
    private val itemFactory: ItemFactory by inject()

    override fun execute() {
        val house = House.getInstance()
        when (what) {
            "floor" -> house.addFloor(Floor(name = params.getOrDefault("-name", "")))
            "room" -> house.addRoom(Room(name = params.getOrDefault("-name", "")), params["-fId"]!!.toLong())
            "item" -> {
                val roomId = params["-rId"]!!.toLong()
                var item : Item? = null
                when(params["-type"]) {
                    ItemType.SKI.value -> item = itemFactory.createSki()
                    ItemType.BICYCLE.value -> item = itemFactory.createBicycle()
                }

                if(item == null) throw CommandExecutionException("Invalid item type")

                house.addItem(item, roomId)
            }
            "livingBeing" -> {
                val roomId = params["-rId"]!!.toLong()
                var livingBeing : LivingBeing? = null
                val name : String? = params.getOrDefault("-name", null)
                when(params["-type"]) {
                    LivingBeingType.HUMAN.value -> livingBeing = livingBeingFactory.createHuman(name)
                    LivingBeingType.DOG.value -> livingBeing = livingBeingFactory.createDog(name)
                    LivingBeingType.CAT.value -> livingBeing = livingBeingFactory.createCat(name)
                }

                if(livingBeing == null) throw CommandExecutionException("Invalid living being type")

                house.addLivingBeing(livingBeing, roomId)
            }
            "device" -> {
                val roomId = params["-rId"]!!.toLong()
                val consumptionUp = params["-cu"]!!.toDouble()
                val consumptionProcessing = params["-cp"]!!.toDouble()
                val consumptionIdle = params["-ci"]!!.toDouble()
                var device : Device? = null
                when (params["-type"]) {
                    DeviceType.WINDOW.value -> {
                        device = deviceFactory.createWindow(Consumption(consumptionUp, consumptionIdle, consumptionProcessing))
                    }
                    DeviceType.DOOR.value -> {
                        device = deviceFactory.createDoor(Consumption(consumptionUp, consumptionIdle, consumptionProcessing))
                    }
                    DeviceType.SMART_PHONE.value -> {
                        device = deviceFactory.createSmartPhone(Consumption(consumptionUp, consumptionIdle, consumptionProcessing))
                    }
                    DeviceType.GARDEN_SPRINKLER.value -> {
                        device = deviceFactory.createGardenSprinkler(Consumption(consumptionUp, consumptionIdle, consumptionProcessing))
                    }
                    DeviceType.WASHING_MACHINE.value -> {
                        device = deviceFactory.createWashingMachine(Consumption(consumptionUp, consumptionIdle, consumptionProcessing))
                    }
                    DeviceType.WEATHER_STATION.value -> {
                        device = deviceFactory.createWeatherStation(Consumption(consumptionUp, consumptionIdle, consumptionProcessing))
                    }
                    DeviceType.KETTLE.value -> {
                        device = deviceFactory.createKettle(Consumption(consumptionUp, consumptionIdle, consumptionProcessing))
                    }
                    DeviceType.ELECTRIC_TOOTHBRUSH.value -> {
                        device = deviceFactory.createElectricToothbrush(Consumption(consumptionUp, consumptionIdle, consumptionProcessing))
                    }
                }

                if(device == null) throw CommandExecutionException("Invalid device type")

                house.addDevice(device, roomId)
            }
        }
    }

    override fun parse(line: String) {
        val parts = line.split(" ")
        if (parts.size == 1) throw CommandParsingException("Command too short. Specify the <what>")

        parseParams(parts)
        what = parts[1]
        validateParams()
    }

    override fun validateParams() {
        when (what) {
            "floor" -> {
                // No checking for floor, it's as dumb as it gets.
            }
            "room" -> {
                if (!params.containsKey("-fId")) {
                    throw CommandParsingException("Required parameter -fId not set.")
                }
            }
            "device" -> {
                validateBasicParams()
                if(!params.containsKey("-ci")) {
                    throw CommandParsingException("Required parameter -ci (consumption idle) not set.")
                }
                if(!params.containsKey("-cp")) {
                    throw CommandParsingException("Required parameter -cp (consumption processing) not set.")
                }
                if(!params.containsKey("-cu")) {
                    throw CommandParsingException("Required parameter -cu (consumption up) not set.")
                }
            }
            "livingBeing" -> {
                validateBasicParams()
            }
            "item" -> {
                validateBasicParams()
            }
            else -> {
                throw CommandParsingException("The <what> is invalid. Valid <what>'s are: floor, room, device, livingBeing")
            }
        }
    }

    private fun validateBasicParams() {
        if (!params.containsKey("-rId")) {
            throw CommandParsingException("Required parameter -rId not set.")
        }
        if(!params.containsKey("-type")) {
            throw CommandParsingException("Required parameter -type not set.")
        }
    }
}