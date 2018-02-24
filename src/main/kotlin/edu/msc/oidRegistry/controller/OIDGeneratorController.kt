package edu.msc.oidRegistry.controller

import edu.msc.oidRegistry.domain.DataNotFoundException
import edu.msc.oidRegistry.model.GeneratorMetadata
import edu.msc.oidRegistry.model.OIDNode
import edu.msc.oidRegistry.service.GeneratorService
import edu.msc.oidRegistry.service.OIDNodeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

/**
 *
 *
 * @Created - 12/10/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@RestController
@RequestMapping("/generator")
class OIDGeneratorController {
    @Autowired
    lateinit var generatorService: GeneratorService

    @Autowired
    lateinit var oidNodeService: OIDNodeService

    @PostMapping()
    fun createNewGenerator(@Valid @RequestBody generator:GeneratorMetadata ): GeneratorMetadata {
        val node = oidNodeService.getNode(generator.node.oid)
        if (node!!.isPresent)
            return  generatorService.createNewGenerator(node.get(), generator.bizKeyDelimiter, generator.allowKeyUpdates)
        else
            throw DataNotFoundException("Node not found - ${generator.node.oid}")
    }

    @PostMapping("{oid:.+}/generate")
    fun generateNewOid(@PathVariable oid:String, @RequestBody node: OIDNode): OIDNode {
        return generatorService.generateNewOid(oid, node.bizKey, node.description)
    }

    @GetMapping("/{oid:.+}")
    fun getGenerator(@PathVariable oid: String): Optional<GeneratorMetadata>? {
        return generatorService.getGeneratorForNode(oid)
    }

    @DeleteMapping("{oid:.+}")
    fun deleteGenerator(@PathVariable oid: String, @RequestParam version: Int) {
        generatorService.delete(oid, version)
    }
}