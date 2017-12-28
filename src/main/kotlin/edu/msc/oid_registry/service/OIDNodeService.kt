package edu.msc.oid_registry.service

import edu.msc.oid_registry.model.OIDNode
import edu.msc.oid_registry.repository.OIDNodeRepository
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
        return repo.save(node)
    }

    fun getNode(oid: String): Optional<OIDNode>? {
        return repo.findById(oid)
    }

    fun updateNode(node: OIDNode): OIDNode {
        return repo.save(node)
    }

    fun deleteNode(oid: String) {
        repo.deleteById(oid)
    }

    fun getChildrenOf(oid: String): List<OIDNode> {
        return repo.findByOidStartsWith(oid)
    }

    fun getNodesByBizKey(bizKey: String): List<OIDNode> {
        return repo.findByBizKey(bizKey)
    }

    fun getChildrenNodeByBizKey(parentOID: String, bizKey: String): OIDNode {
       //Make sure there's a dot at thend so not to get other branches!
        var parentOIDWithSeg = if (parentOID.endsWith("."))  parentOID else parentOID + ".";
        return repo.findByBizKeyAndParentOid(parentOIDWithSeg, bizKey)
    }

}