package cz.cvut.fel.omo.smarthome.model.livingbeing

import cz.cvut.fel.omo.smarthome.logger.LogSeverity
import cz.cvut.fel.omo.smarthome.model.device.GardenSprinkler
import cz.cvut.fel.omo.smarthome.simulation.Simulation
import kotlin.random.Random

class Dog : Animal() {
    private fun breakSprinklers() {
        val sprinklerSearchResult = Simulation.findDeviceByType<GardenSprinkler>(this)
        if(sprinklerSearchResult != null) {
            sprinklerSearchResult.currRoom.leave(this)
            sprinklerSearchResult.targetRoom.enter(this)
            if(sprinklerSearchResult.device.damage()) {
                logger.log(LogSeverity.INFO, "$name fucked up sprinklers.")
            }
        }
    }

    override fun run() {
        if(Random.nextInt(0, 10) == 5) {
            breakSprinklers()
        }
    }
}