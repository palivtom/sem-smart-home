package cz.cvut.fel.omo.smarthome.dependencyinjection

import cz.cvut.fel.omo.smarthome.logger.Logger
import org.koin.dsl.module

val loggerModule = module {
    single { Logger() }
}