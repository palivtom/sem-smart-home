package cz.cvut.fel.omo.smarthome.model.messaging

enum class MessageType {
    ROOM_ENTERED, ROOM_LEFT,
    WEATHER_RAINING, WEATHER_OTHER, // basically england
    SPRINKLERS_ON, SPRINKLERS_OFF,
    WASHING_FINISHED,
    DEVICE_BROKE,
    DOORS_CLOSED, DOORS_OPENED,
    WINDOWS_CLOSED, WINDOWS_OPENED,
    DOOR_OPENED, DOOR_CLOSED
}