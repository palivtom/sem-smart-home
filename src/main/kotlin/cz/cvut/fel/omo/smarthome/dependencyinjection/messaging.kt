package cz.cvut.fel.omo.smarthome.dependencyinjection

import cz.cvut.fel.omo.smarthome.model.messaging.MessageManager
import org.koin.dsl.module

val messagingModule = module {
    single { MessageManager() }
}