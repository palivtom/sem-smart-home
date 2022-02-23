package cz.cvut.fel.omo.smarthome.cli.commands

interface ICommand {
    fun execute()
    fun parse(line: String)
}