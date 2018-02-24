package edu.msc.oidRegistry.model

import javax.persistence.*
/**
 *
 *
 * @Created - 12/10/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Entity
data class GeneratorMetadata(@field:OneToOne(cascade = arrayOf(CascadeType.DETACH))
                             @field:JoinColumn(name="oid") val node: OIDNode,
                             @field:Column val bizKeyDelimiter:String = "^",
                             @field:Column val allowKeyUpdates:Boolean = false
                             ) {

    private constructor() : this(OIDNode("1.1", "temp"))

    @Id
    @GeneratedValue
    var generatorId: Int = 1

    @Column
    var nextChildSequenceNumber: Int = 1

    @Version
    var version: Int = 1

}