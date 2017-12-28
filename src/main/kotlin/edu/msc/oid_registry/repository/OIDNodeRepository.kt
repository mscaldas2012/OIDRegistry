package edu.msc.oid_registry.repository

import edu.msc.oid_registry.model.OIDNode
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 *
 *
 * @Created - 12/19/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface OIDNodeRepository : CrudRepository<OIDNode, String> {

    fun findByOidStartsWith(oid: String): List<OIDNode>

    fun findByBizKey(bizKey: String): List<OIDNode>

    @Query("from OIDNode n where n.oid like :parentOID%  and n.bizKey = :bizKey")
    fun findByBizKeyAndParentOid(parentOID: String, bizKey: String): OIDNode
}