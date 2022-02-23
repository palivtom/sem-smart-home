package cz.cvut.fel.omo.smarthome.dependencyinjection

import cz.cvut.fel.omo.smarthome.factory.DeviceFactory
import cz.cvut.fel.omo.smarthome.factory.ItemFactory
import cz.cvut.fel.omo.smarthome.factory.LivingBeingFactory
import cz.cvut.fel.omo.smarthome.model.messaging.MessageFactory
import org.koin.dsl.module

val factoryModule = module {
    factory { DeviceFactory() }
    factory { LivingBeingFactory() }
    factory { MessageFactory() }
    factory { ItemFactory() }
}