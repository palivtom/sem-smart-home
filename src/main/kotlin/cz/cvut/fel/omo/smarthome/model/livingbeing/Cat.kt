package cz.cvut.fel.omo.smarthome.model.livingbeing

import cz.cvut.fel.omo.smarthome.logger.LogSeverity
import cz.cvut.fel.omo.smarthome.model.device.Door
import cz.cvut.fel.omo.smarthome.model.item.Ski
import cz.cvut.fel.omo.smarthome.simulation.Simulation
import kotlin.random.Random

class Cat : Animal() /*, Table() https://bit.ly/3JpjRnn*/ {
    private fun breakDoors() {
        val doorSearchResult = Simulation.findDeviceByType<Door>(this)
        if(doorSearchResult != null) {
            doorSearchResult.currRoom.leave(this)
            doorSearchResult.targetRoom.enter(this)
            if(doorSearchResult.device.damage()) {
                logger.log(LogSeverity.INFO, "$name damaged doors")
            }
        }
    }

    private fun dropSkis() {
        val skiSearchResult = Simulation.findItemByType<Ski>(this)
        if(skiSearchResult != null && skiSearchResult.targetRoom == skiSearchResult.currRoom) {
            if (skiSearchResult.item.dropSki()) {
                logger.log(LogSeverity.INFO, "$name dropped skis")
            }
        }
    }

    override fun run() {
        if(Random.nextInt(0, 10) == 5) {
            breakDoors()
        }
        if(Random.nextInt(0, 10) == 5) {
            dropSkis()
        }
    }
}