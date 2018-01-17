package edu.msc.oid_registry.repository

import edu.msc.oid_registry.model.GeneratorMetadata
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

/**
 *
 *
 * @Created - 1/13/18
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface GeneratorMetadataRepository:CrudRepository<GeneratorMetadata, Long> {
    fun findByNodeOid(oid: String): Optional<GeneratorMetadata>
}