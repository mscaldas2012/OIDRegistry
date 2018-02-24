package edu.msc.oidRegistry.service

import edu.msc.oidRegistry.domain.DataNotFoundException
import edu.msc.oidRegistry.model.OIDNode
import edu.msc.oidRegistry.repository.OIDNodeRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

/**
 *
 *
 * @Created - 12/24/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Service
@Transactional
class OIDNodeService(val repo: OIDNodeRepository) {

    fun registerNewOid(node: OIDNode):OIDNode {
        //TODO::See if there's no Generator for the parent of this Node.
        //  If there is, force user to use Generator.createNewNode
        return repo.save(node)
    }

    fun getNode(oid: String): Optional<OIDNode>? {
        return repo.findById(oid)
    }
    fun updateDescription(oid: String, newDescription: String): OIDNode {
        val node = getNode(oid)
        if (node!!.isPresent) {
            node.get().description = newDescription
            return repo.save(node.get())
        } else {
            throw DataNotFoundException("Node not found for OID ${oid}")
        }

    }

    fun deleteNode(oid: String, version: Int) {
        val node = getNode(oid)
        if (!node!!.isPresent) {
            throw DataNotFoundException("Node not found for OID ${oid}")
        }
        //TODO::Add version control for optimistic locking.
        repo.deleteById(oid)
    }

    fun getChildrenOf(parentOID: String): List<OIDNode> {
        //val parentOIDWithSeg = if (parentOID.endsWith("."))  parentOID else parentOID + ".";
        return repo.findByParent(parentOID)
    }

    fun getChildrenNodeByBizKey(parentOID: String, bizKey: String): Optional<OIDNode>? {
       //Make sure there's a dot at thend so not to get other branches!
        //val parentOIDWithSeg = if (parentOID.endsWith("."))  parentOID else parentOID + "."
        return repo.findByParentAndBizKey(parentOID, bizKey)
    }

    fun search(bizKey: String): List<OIDNode> {
        return repo.findByBizKeyContains(bizKey)
    }

    fun searchChildren(oid: String, bizKey: String): List<OIDNode> {
        return repo.findByParentAndBizKeyContains(oid, bizKey)
    }

}