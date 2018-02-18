package edu.msc.oidRegistry.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 *
 *
 * @Created - 12/28/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder("status", "timestamp", "path", "description", "exception")
data class ErrorMessage(  val status: Int, val path: String ) {
    val timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT)

    lateinit var description: String
    var exception: String? = null
}