package cz.cvut.fel.omo.smarthome.cli.commands

abstract class Command : ICommand {
    protected var params = mutableMapOf<String, String>()

    protected open fun parseParams(parts: List<String>) {
        for (part in parts) {
            val keyValue = part.split("=")
            if (keyValue.size == 2) params[keyValue[0]] = keyValue[1]
            else if(part.contains("=")) println("Weirdly formatted param ${parts[0]} detected. Maybe a typo?")
        }
    }

    protected abstract fun validateParams()
}