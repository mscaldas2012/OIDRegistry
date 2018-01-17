package edu.msc.oid_registry.service

import edu.msc.oid_registry.domain.DataNotFoundException
import edu.msc.oid_registry.model.GeneratorMetadata
import edu.msc.oid_registry.model.OIDNode
import edu.msc.oid_registry.repository.GeneratorMetadataRepository
import edu.msc.oid_registry.repository.OIDNodeRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

/**
 *
 *
 * @Created - 1/13/18
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@Service
@Transactional
class GeneratorService(val generatorMetadataRepository: GeneratorMetadataRepository, val oidRepository: OIDNodeRepository) {

    //TODO::Check if there's a Generator for that Node - Only one Generator shall exist per Node!
    fun createNewGenerator(node: OIDNode, bizKeyDelimiter: String = "^", allowBizKeyUpdates: Boolean = false):GeneratorMetadata {
        val generator = GeneratorMetadata(node, bizKeyDelimiter, allowBizKeyUpdates)

        return generatorMetadataRepository.save(generator)
    }

    fun getGeneratorForNode(oid: String): Optional<GeneratorMetadata>? {
        return generatorMetadataRepository.findByNodeOid(oid)
    }

    fun generateNewOid(parentNodeOid: String, bizKey: String): OIDNode {
        //Get the Genrator for oid:
        val findGen: Optional<GeneratorMetadata>? = this.getGeneratorForNode(parentNodeOid)
        if (!findGen!!.isPresent)
            throw DataNotFoundException("Unable to find Generator for Node ${parentNodeOid}")

        val generator = findGen.get()
        val newOid = OIDNode(generator.node.oid + "." + generator.nextChildSequenceNumber, bizKey)
        generator.nextChildSequenceNumber++
        generatorMetadataRepository.save(generator)
        return oidRepository.save(newOid)

    }

}