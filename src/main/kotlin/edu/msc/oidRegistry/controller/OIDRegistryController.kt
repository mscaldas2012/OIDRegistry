package edu.msc.oidRegistry.controller

import edu.msc.oidRegistry.domain.DataNotFoundException
import edu.msc.oidRegistry.model.OIDNode
import edu.msc.oidRegistry.service.OIDNodeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 *
 *
 * @Created - 12/8/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */

@RestController
@RequestMapping("/object")
class OIDRegistryController {

    @Autowired
    lateinit var service: OIDNodeService

    @PostMapping("")
    fun registerNewOid(@Valid @RequestBody node: OIDNode): OIDNode {
        val newNode = service.registerNewOid(node)
        return newNode
    }

    @GetMapping("/{oid:.+}")
    fun getOID(@PathVariable oid: String): ResponseEntity<OIDNode> {
        val node =  service.getNode(oid)

        if(node!!.isPresent) {
            return ResponseEntity.ok(node.get())
        }
        else {
            throw  DataNotFoundException("Unable to find Object for OID " + oid)
        }
    }
    @PutMapping("/{oid:.+}")
    fun updateOID(@PathVariable oid: String, @RequestBody node: OIDNode) {
        //TODO: Can update only description and bizKey if Generator allows.
    }

    @DeleteMapping("/{oid:.+}")
    fun deleteOID(@PathVariable oid: String, @RequestParam version: Int) {
        service.deleteNode(oid, version)

    }

    @GetMapping("/{oid}/children")
    fun getChildren(@PathVariable oid: String): List<OIDNode> {
        return service.getChildrenOf(oid)
    }

    @GetMapping("search")
    fun search(bizKey: String): List<OIDNode> {
        return service.search(bizKey)
    }

    @GetMapping("/{oid:.+}/search")
    fun searchChildren(@PathVariable oid: String, bizKey:String ): List<OIDNode> {
        return service.searchChildren(oid, bizKey)
    }

}