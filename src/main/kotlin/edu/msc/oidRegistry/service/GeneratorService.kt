package edu.msc.oidRegistry.service

import edu.msc.oidRegistry.domain.ConflictException
import edu.msc.oidRegistry.domain.DataNotFoundException
import edu.msc.oidRegistry.model.GeneratorMetadata
import edu.msc.oidRegistry.model.OIDNode
import edu.msc.oidRegistry.repository.GeneratorMetadataRepository
import edu.msc.oidRegistry.repository.OIDNodeRepository
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

    fun createNewGenerator(node: OIDNode, bizKeyDelimiter: String = "^", allowBizKeyUpdates: Boolean = false):GeneratorMetadata {
        val generator = GeneratorMetadata(node, bizKeyDelimiter, allowBizKeyUpdates)
        val existent = this.getGeneratorForNode(node.oid)
        if (existent!!.isPresent) {
            throw ConflictException("Generator already register for Node ${node.oid}" )
        }
        return generatorMetadataRepository.save(generator)
    }

    fun getGeneratorForNode(oid: String): Optional<GeneratorMetadata>? {
        return generatorMetadataRepository.findByNodeOid(oid)
    }

    fun generateNewOid(parentNodeOid: String, bizKey: String, description: String? = ""): OIDNode {
        //Get the Genrator for oid:
        val findGen = this.getGeneratorForNode(parentNodeOid)
        if (!findGen!!.isPresent)
            throw DataNotFoundException("Unable to find Generator for Node ${parentNodeOid}")

        val generator = findGen.get()
        val newOid = OIDNode(generator.node.oid + "." + generator.nextChildSequenceNumber, bizKey)
        newOid.description = description
        generator.nextChildSequenceNumber++
        generatorMetadataRepository.save(generator)
        return oidRepository.save(newOid)

    }

}