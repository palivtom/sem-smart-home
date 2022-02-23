package cz.cvut.fel.omo.smarthome.logger

import cz.cvut.fel.omo.smarthome.export.ExportVisitor
import java.time.LocalDateTime
import java.util.*

class Logger {
    private val logHistory : Queue<String> = ArrayDeque()

    fun log(severity: LogSeverity, content: String) {
        val builder = LogBuilder().addTimestamp(LocalDateTime.now()).addSeverity(severity).addContent(content)
        logHistory.add(builder.build())
    }

    fun accept(exporter: ExportVisitor) {
        exporter.export(this)
    }

    fun getLogHistory(): MutableList<String> {
        return logHistory.toMutableList()
    }

    fun clear() {
        logHistory.clear()
    }
}