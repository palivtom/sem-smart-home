package cz.cvut.fel.omo.smarthome.export

import cz.cvut.fel.omo.smarthome.exception.ImportException
import cz.cvut.fel.omo.smarthome.factory.DeviceFactory
import cz.cvut.fel.omo.smarthome.factory.LivingBeingFactory
import cz.cvut.fel.omo.smarthome.model.building.Floor
import cz.cvut.fel.omo.smarthome.model.building.Room
import cz.cvut.fel.omo.smarthome.model.device.utils.Consumption
import cz.cvut.fel.omo.smarthome.model.device.Device
import cz.cvut.fel.omo.smarthome.model.item.Bicycle
import cz.cvut.fel.omo.smarthome.model.item.Item
import cz.cvut.fel.omo.smarthome.model.item.Ski
import cz.cvut.fel.omo.smarthome.model.livingbeing.LivingBeing
import org.json.JSONArray
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.io.FileInputStream
import java.io.InputStream


class JSONImporter : KoinComponent {
    private val deviceFactory: DeviceFactory by inject()
    private val livingBeingFactory: LivingBeingFactory by inject()

    fun import(path: String): MutableList<Floor> {
        val devices = mutableListOf<Device>()
        val rooms = mutableListOf<Room>()
        val floors = mutableListOf<Floor>()
        val livingBeings = mutableListOf<LivingBeing>()
        val items = mutableListOf<Item>()

        val file = File(path)
        var root : JSONObject? = null
        if (file.exists()) {
            val inputStream: InputStream = FileInputStream(path)
            val jsonTxt: String = inputStream.bufferedReader().use { it.readText() }
            root = JSONObject(jsonTxt)
        }

        if(root == null)
            throw ImportException("File with path \"${path}\" does not exist")

        for (device in root.get("deviceDict") as JSONArray) {
            val deviceObj = device as JSONObject
            var addedDevice : Device? = null
            // TODO: consumption
            val consumptionObj = deviceObj.get("consumption") as JSONObject

            val up = consumptionObj.getDouble("up")
            val idle = consumptionObj.getDouble("idle")
            val processing = consumptionObj.getDouble("processing")

            when(deviceObj.get("type")) {
                DeviceType.WINDOW.value -> {
                    addedDevice = deviceFactory.createWindow(Consumption(up, idle, processing))
                }
                DeviceType.DOOR.value -> {
                    addedDevice = deviceFactory.createDoor(Consumption(up, idle, processing))
                }
                DeviceType.SMART_PHONE.value -> {
                    addedDevice = deviceFactory.createSmartPhone(Consumption(up, idle, processing))
                }
                DeviceType.GARDEN_SPRINKLER.value -> {
                    addedDevice = deviceFactory.createGardenSprinkler(Consumption(up, idle, processing))
                }
                DeviceType.WASHING_MACHINE.value -> {
                    addedDevice = deviceFactory.createWashingMachine(Consumption(up, idle, processing))
                }
                DeviceType.WEATHER_STATION.value -> {
                    addedDevice = deviceFactory.createWeatherStation(Consumption(up, idle, processing))
                }
                DeviceType.KETTLE.value -> {
                    addedDevice = deviceFactory.createKettle(Consumption(up, idle, processing))
                }
                DeviceType.ELECTRIC_TOOTHBRUSH.value -> {
                    addedDevice = deviceFactory.createElectricToothbrush(Consumption(up, idle, processing))
                }
            }

            if(addedDevice == null)
                throw ImportException("Device has invalid type")

            addedDevice = addedDevice.apply {
                id = (deviceObj.get("id") as Int).toLong()
            }

            devices.add(addedDevice)
        }

        for(livingBeing in root.get("livingBeingDict") as JSONArray) {
            val livingBeingObj = livingBeing as JSONObject
            var addedLivingBeing : LivingBeing? = null
            val name = livingBeingObj.get("name") as String
            when(livingBeingObj.get("type")) {
                LivingBeingType.CAT.value -> {
                    addedLivingBeing = livingBeingFactory.createCat(name)
                }
                LivingBeingType.DOG.value -> {
                    addedLivingBeing = livingBeingFactory.createDog(name)
                }
                LivingBeingType.HUMAN.value -> {
                    addedLivingBeing = livingBeingFactory.createHuman(name)
                }
            }

            if(addedLivingBeing == null)
                throw ImportException("Living being has invalid type")

            addedLivingBeing = addedLivingBeing.apply {
                id = (livingBeingObj.get("id") as Int).toLong()
            }

            livingBeings.add(addedLivingBeing)
        }


        for (item in root.get("itemDict") as JSONArray) {
            val itemObj = item as JSONObject
            var addedItem : Item? = null
            when(item.get("type")) {
                ItemType.SKI.value -> addedItem = Ski()
                ItemType.BICYCLE.value -> addedItem = Bicycle()
            }

            if(addedItem == null) throw ImportException("Invalid item type")

            addedItem.id = itemObj.getLong("id")
            items.add(addedItem)
        }

        for(room in root.get("roomDict") as JSONArray) {
            val roomObj = room as JSONObject
            val addedRoom = Room().apply {
                id = (roomObj.get("id") as Int).toLong()
                name = roomObj.get("name") as String
            }

            for(deviceId in roomObj.get("deviceIds") as JSONArray) {
                val deviceIdInt = (deviceId as Int).toLong()
                val device = devices.find { it.id == deviceIdInt }
                if(device != null) addedRoom.devices.add(device)
                else
                    throw ImportException("Device with this ID was not found in device dictionary")
            }

            for(livingBeingId in roomObj.get("livingBeingIds") as JSONArray) {
                val livingBeingIdInt = (livingBeingId as Int).toLong()
                val livingBeing = livingBeings.find { it.id == livingBeingIdInt }
                if(livingBeing != null) addedRoom.livingBeings.add(livingBeing)
                else
                    throw ImportException("Living being with this ID was not found in living being dictionary")
            }

            for(itemId in roomObj.get("itemIds") as JSONArray) {
                val item = items.find { it.id == (itemId as Int).toLong() }
                if(item != null) addedRoom.items.add(item)
                else throw ImportException("Item with this ID was not found in item dictionary")
            }

            rooms.add(addedRoom)
        }

        for(floor in root.get("floors") as JSONArray) {
            val floorObj = floor as JSONObject
            val addedFloor = Floor().apply {
                id = (floorObj.get("id") as Int).toLong()
                name = floorObj.get("name") as String
            }

            // Match floors and rooms
            for(roomId in floorObj.get("roomIds") as JSONArray) {
                val roomIdInt = (roomId as Int).toLong()
                val room = rooms.find { it.id == roomIdInt }
                if(room != null) addedFloor.rooms.add(room)
                else
                    throw ImportException("This file does not exist")
            }
            floors.add(addedFloor)
        }

        return floors
    }
}