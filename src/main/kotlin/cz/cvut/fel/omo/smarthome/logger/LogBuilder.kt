package cz.cvut.fel.omo.smarthome.logger

import cz.cvut.fel.omo.smarthome.exception.SmartHomeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class LogBuilder : ILogBuilder {
    private var timestamp: LocalDateTime? = null
    private var severity: LogSeverity? = null
    private var content: String = ""

    override fun addTimestamp(timestamp: LocalDateTime): ILogBuilder {
        this.timestamp = timestamp
        return this
    }

    override fun addSeverity(severity: LogSeverity): ILogBuilder {
        this.severity = severity
        return this
    }

    override fun addContent(content: String): ILogBuilder {
        this.content = content
        return this
    }

    override fun build(): String {
        if(timestamp == null || severity == null) throw SmartHomeException("Invalid LogBuilder setup")

        return "${timestamp.toString()} ${severity!!.value} $content"
    }
}