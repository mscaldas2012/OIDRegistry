package edu.msc.oidRegistry.model

import com.fasterxml.jackson.annotation.JsonIgnore
import edu.msc.oidRegistry.OIDUtils
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
/**
 *
 *
 * @Created - 12/7/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Entity

data class OIDNode (@Id @field:NotEmpty(message = "OID is Required") @field:Pattern(regexp= OIDUtils.OID_REGEX, message= "Invalid OID passed")var oid: String,
                     @field:NotEmpty(message = "Business Key is required") var bizKey: String ) {
    @Column
    var description: String? = null

    @JsonIgnore
    @Access(AccessType.PROPERTY)
    var parent: String? = null
        get() = oid.substring(0, Math.max(oid.lastIndexOf('.'),0))

    private constructor() : this("2.1", "")

 }