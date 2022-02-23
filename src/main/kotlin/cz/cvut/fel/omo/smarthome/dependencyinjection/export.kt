package cz.cvut.fel.omo.smarthome.dependencyinjection

import cz.cvut.fel.omo.smarthome.export.JSONExporter
import cz.cvut.fel.omo.smarthome.export.JSONImporter
import cz.cvut.fel.omo.smarthome.export.XMLExporter
import org.koin.dsl.module

val exportModule = module {
    factory { JSONExporter() }
    factory { XMLExporter() }
    factory { JSONImporter() }
}