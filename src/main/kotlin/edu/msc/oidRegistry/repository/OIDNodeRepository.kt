package edu.msc.oidRegistry.repository

import edu.msc.oidRegistry.model.OIDNode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

/**
 *
 *
 * @Created - 12/19/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface OIDNodeRepository : JpaRepository<OIDNode, String> {

    fun findByOidStartsWith(oid: String): List<OIDNode>

    fun findByParent(parentOID: String): List<OIDNode>

    fun findByBizKeyContains(bizKey: String): List<OIDNode>

    //@Query("from OIDNode n where n.oid like :parentOID%  and n.bizKey = :bizKey")
    fun findByParentAndBizKey(parent: String, bizKey: String): Optional<OIDNode>?

    fun findByParentAndBizKeyContains(parent: String, bizKey: String): List<OIDNode>
}