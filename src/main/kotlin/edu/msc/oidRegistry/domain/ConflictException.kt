package edu.msc.oidRegistry.domain

import org.springframework.context.MessageSourceResolvable

/**
 *
 *
 * @Created - 2/17/18
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
class ConflictException(message: String?) : RuntimeException(message), MessageSourceResolvable {
    override fun getCodes(): Array<String> =
            arrayOf("error.conflict")

}