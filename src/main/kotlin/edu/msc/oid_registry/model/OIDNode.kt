package edu.msc.oid_registry.model

import edu.msc.oid_registry.OIDUtils
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
/**
 *
 *
 * @Created - 12/7/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Entity
data class OIDNode (@Id @field:NotEmpty(message = "OID is Required") @field:Pattern(regexp= OIDUtils.OID_REGEX, message= "Invalid OID passed")val oid: String,
                     @field:NotEmpty(message = "Business Key is required") val bizKey: String ) {
    @Column
    var description: String? = null

    private constructor() : this("", "")


}