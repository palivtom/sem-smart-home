package cz.cvut.fel.omo.smarthome.export

import cz.cvut.fel.omo.smarthome.logger.Logger
import cz.cvut.fel.omo.smarthome.model.building.House
import cz.cvut.fel.omo.smarthome.model.device.*
import cz.cvut.fel.omo.smarthome.model.item.Bicycle
import cz.cvut.fel.omo.smarthome.model.item.Ski
import cz.cvut.fel.omo.smarthome.model.livingbeing.Cat
import cz.cvut.fel.omo.smarthome.model.livingbeing.Dog
import cz.cvut.fel.omo.smarthome.model.livingbeing.Human
import cz.cvut.fel.omo.smarthome.model.messaging.MessageManager
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.lang.IllegalStateException

class JSONExporter : ExportVisitor {
    var path : String = ""
    override fun export(house: House) {
        if(path == "") throw IllegalStateException()
        val root = JSONObject()

        root.put("floors",          exportFloors(house))
        root.put("deviceDict",      createDeviceDict(house))
        root.put("roomDict",        createRoomDict(house))
        root.put("livingBeingDict", createLivingBeingDict(house))
        root.put("itemDict",        createItemDict(house))

        saveToFile(root, path)
    }

    override fun export(messageManager: MessageManager) {
       if(path == "") throw IllegalStateException()

        val root = JSONObject()

        val messages = JSONArray()
        for(message in messageManager.getMessageHistory()) {
            val messageObj = JSONObject()
            val sourceObj = JSONObject()
            sourceObj.put("id", message.source.id)
            sourceObj.put("type", message.source.sourceType.toString())
            messageObj.put("source", sourceObj)
            messageObj.put("message", message.type)
            messages.put(messageObj)
        }

        root.put("messages", messages)

        saveToFile(root, path)
    }


    override fun export(logger: Logger) {
        if(path == "") throw IllegalStateException()

        val root = JSONObject()
        val messages = JSONArray()

        logger.getLogHistory().forEach{
            messages.put(it)
        }

        root.put("messages", messages)

        saveToFile(root, path)
    }

    override fun export(electricityMeter: ElectricityMeter) {
        if(path == "") throw IllegalStateException()

        val root = JSONObject()
        val messages = JSONArray()

        electricityMeter.getHistory().forEach{
            val messageObj = JSONObject()
            messageObj.put("date", it.date.toString())
            messageObj.put("consuption", it.consumption)
            messages.put(messageObj)
        }

        root.put("history", messages)

        saveToFile(root, path)
    }

    private fun saveToFile(root:JSONObject, path: String) {
        val file = File(path)
        file.writeText("")
        file.writeText(root.toString())
    }

    private fun exportFloors(house: House): JSONArray {
        val rv = JSONArray()

        house.getFloorIterator().forEach { floor ->
            val floorObj = JSONObject()
            floorObj.put("id", floor.id)
            floorObj.put("name", floor.name)

            val roomIdsArr = JSONArray()
            for(room in floor.rooms) {
                roomIdsArr.put(room.id)
            }

            floorObj.put("roomIds", roomIdsArr)

            rv.put(floorObj)
        }

        return rv
    }

    private fun createDeviceDict(house: House) : JSONArray {
        val rv = JSONArray()
        val it = house.getDeviceIterator()
        while (it.hasNext()) {
            val device = it.next()
            val deviceObj = JSONObject()

            val consumptionObj = JSONObject()
            consumptionObj.put("up", device.getExportConsumption().up)
            consumptionObj.put("idle", device.getExportConsumption().idle)
            consumptionObj.put("processing", device.getExportConsumption().processing)

            deviceObj.put("consumption", consumptionObj)

            deviceObj.put("id", device.id)
            // TODO: Consumption?
            when(device) {
                is Window -> deviceObj.put("type", DeviceType.WINDOW)
                is Door -> deviceObj.put("type", DeviceType.DOOR)
                is SmartPhone -> deviceObj.put("type", DeviceType.SMART_PHONE)
                is GardenSprinkler -> deviceObj.put("type", DeviceType.GARDEN_SPRINKLER)
                is WashingMachine -> deviceObj.put("type", DeviceType.WASHING_MACHINE)
                is WeatherStation -> deviceObj.put("type", DeviceType.WEATHER_STATION)
                is ElectricToothbrush -> deviceObj.put("type", DeviceType.ELECTRIC_TOOTHBRUSH)
                is Kettle -> deviceObj.put("type", DeviceType.KETTLE)
            }
            rv.put(deviceObj)
        }
        return rv
    }

    private fun createRoomDict(house: House) : JSONArray {
        val rv = JSONArray()
        val it = house.getRoomIterator()
        while(it.hasNext()) {
            val roomObj = JSONObject()
            val room = it.next()
            roomObj.put("id", room.id)
            roomObj.put("name", room.name)

            val deviceIdArr = JSONArray()
            for(device in room.devices) {
                deviceIdArr.put(device.id)
            }

            val livingBeingIdsArr = JSONArray()
            for(livingBeing in room.livingBeings) {
                livingBeingIdsArr.put(livingBeing.id)
            }

            val itemIdsArr = JSONArray()
            for(item in room.items) {
                itemIdsArr.put(item.id)
            }

            roomObj.put("deviceIds", deviceIdArr)
            roomObj.put("livingBeingIds", livingBeingIdsArr)
            roomObj.put("itemIds", itemIdsArr)

            rv.put(roomObj)
        }
        return rv
    }

    private fun createLivingBeingDict(house: House) : JSONArray {
        val rv = JSONArray()
        house.getLivingBeingIterator().forEach { livingBeing ->
            val livingBeingObj = JSONObject()
            livingBeingObj.put("id", livingBeing.id)
            livingBeingObj.put("name", livingBeing.name)
            // TODO: Consumption?
            when(livingBeing) {
                is Cat -> livingBeingObj.put("type", LivingBeingType.CAT)
                is Human -> livingBeingObj.put("type", LivingBeingType.HUMAN)
                is Dog -> livingBeingObj.put("type", LivingBeingType.DOG)
            }
            rv.put(livingBeingObj)
        }

        return rv
    }

    private fun createItemDict(house: House): JSONArray {
        val rv = JSONArray()
        house.getItemIterator().forEach {
            val itemObj = JSONObject()
            itemObj.put("id", it.id)
            when(it) {
                is Ski -> itemObj.put("type", ItemType.SKI)
                is Bicycle -> itemObj.put("type", ItemType.BICYCLE)
            }
            rv.put(itemObj)
        }
        return rv
    }
}