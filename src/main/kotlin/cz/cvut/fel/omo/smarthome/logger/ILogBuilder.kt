package cz.cvut.fel.omo.smarthome.logger

import java.time.LocalDateTime
import java.util.*

interface ILogBuilder {
    fun addTimestamp(timestamp: LocalDateTime) : ILogBuilder
    fun addSeverity(severity: LogSeverity) : ILogBuilder
    fun addContent(content: String) : ILogBuilder
    fun build() : String
}