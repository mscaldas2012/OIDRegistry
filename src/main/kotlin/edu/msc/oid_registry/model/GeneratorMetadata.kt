package edu.msc.oid_registry.model

/**
 *
 *
 * @Created - 12/10/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
class GeneratorMetadata {
    var nextChildSequenceNumber: Int = 1
    var node: OIDNode? = null
    var allowKeyUpdates: Boolean = false
    var bizKeyDelimiter: String? = "^"
    var version: Int = 1

}