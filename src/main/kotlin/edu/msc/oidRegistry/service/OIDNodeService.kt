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
    //Needs to check if parent has a Generator and whether it allows the biz keys to be updated.
    fun updateBizKey(oid: String, newbizKey: String): OIDNode {
        val node = getNode(oid)
        if (node!!.isPresent) {
            node.get().bizKey = newbizKey
            return repo.save(node.get())
        } else {
            throw DataNotFoundException("Node not found for OID ${oid}")
        }

    }

    fun deleteNode(oid: String) {
        repo.deleteById(oid)
    }

    fun getChildrenOf(parentOID: String): List<OIDNode> {
        //val parentOIDWithSeg = if (parentOID.endsWith("."))  parentOID else parentOID + ".";
        return repo.findByParent(parentOID)
    }

    fun getNodesByBizKey(bizKey: String): List<OIDNode> {
        return repo.findByBizKey(bizKey)
    }

    fun getChildrenNodeByBizKey(parentOID: String, bizKey: String): Optional<OIDNode>? {
       //Make sure there's a dot at thend so not to get other branches!
        val parentOIDWithSeg = if (parentOID.endsWith("."))  parentOID else parentOID + ".";
        return repo.findByBizKeyAndParentOid(parentOIDWithSeg, bizKey)
    }

}