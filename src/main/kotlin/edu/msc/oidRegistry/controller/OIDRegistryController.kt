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

    @GetMapping("/{oid}/children")
    fun getChildren(@PathVariable oid: String): ResponseEntity<List<OIDNode>>? {
        return ResponseEntity.ok(service.getChildrenOf(oid));
    }

}