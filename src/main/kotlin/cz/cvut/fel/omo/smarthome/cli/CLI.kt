package cz.cvut.fel.omo.smarthome.cli

import cz.cvut.fel.omo.smarthome.cli.commands.*
import cz.cvut.fel.omo.smarthome.cli.exception.CLIException

class CLI {
    private var run = true

    fun run(args: Array<String>) {
        println("Welcome to project smarthome V1.0.0 CLI.")
        while(run) {
            val line = readln()
            if(line != "") {
                parseCommand(line)
            }
        }
    }

    private fun parseCommand(line: String) {
        val parts = line.split(" ")
        var command: Command? = null
        when(parts[0]) {
            "export" -> command = ExportCommand()
            "import" -> command = ImportCommand()
            "run" -> command = SimulateCommand()
            "add" -> command = AddCommand()
            "list" -> command = ListCommand()
            "exit" -> {
                println("""
 _______
< Bye:) >
 -------
        \   ^__^
         \  (oo)\_______
            (__)\       )\
                ||----w |
                ||     ||
                """.trimIndent())
                run = false
                return
            }
        }

        if(command == null) {
            println("Unrecognized command: ${parts[0]}")
            return
        }

        try {
            command.parse(line)
        }
        catch (e: CLIException) {
            println(e.message)
            return
        }

        try {
            command.execute()
            println("Command successfully finished")
        }
        catch (e: CLIException) {
            println("Command not successfully finished - exception occurred")
            println(e.message)
            return
        }
    }
}