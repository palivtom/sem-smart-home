package cz.cvut.fel.omo.smarthome.export

interface IExportable {
    fun accept(visitor: ExportVisitor)
}