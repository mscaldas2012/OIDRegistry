package edu.msc.oidRegistry.domain

import org.springframework.context.MessageSourceResolvable

/**
 *
 *
 * @Created - 12/28/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
class DataNotFoundException(message: String?) : RuntimeException(message), MessageSourceResolvable {
    override fun getCodes(): Array<String> =
        arrayOf("error.recordNotFound")

}