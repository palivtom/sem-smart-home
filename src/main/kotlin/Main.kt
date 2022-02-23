import cz.cvut.fel.omo.smarthome.cli.CLI
import cz.cvut.fel.omo.smarthome.dependencyinjection.exportModule
import cz.cvut.fel.omo.smarthome.dependencyinjection.factoryModule
import cz.cvut.fel.omo.smarthome.dependencyinjection.loggerModule
import cz.cvut.fel.omo.smarthome.dependencyinjection.messagingModule
import org.koin.core.context.startKoin

fun setupDependencyInjection() {
    startKoin {
        modules(messagingModule, factoryModule, exportModule, loggerModule)
    }
}

fun main(args: Array<String>) {
    setupDependencyInjection();

    val cli = CLI()
    cli.run(args)
}