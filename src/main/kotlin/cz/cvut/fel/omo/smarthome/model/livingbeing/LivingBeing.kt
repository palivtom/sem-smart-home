package cz.cvut.fel.omo.smarthome.model.livingbeing

import cz.cvut.fel.omo.smarthome.logger.Logger
import cz.cvut.fel.omo.smarthome.simulation.ISimulatable
import cz.cvut.fel.omo.smarthome.utils.LongAutoIncrement
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class LivingBeing : ILivingBeing, ISimulatable, KoinComponent {
    protected val logger: Logger by inject()

    companion object {
        val longAutoIncrement: LongAutoIncrement = LongAutoIncrement()
    }

    var id: Long = longAutoIncrement.value
    var name: String = this.javaClass.toString()

}