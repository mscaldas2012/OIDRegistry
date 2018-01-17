package edu.msc.oid_registry.service

import edu.msc.oid_registry.model.OIDNode
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @Created - 1/13/18
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class GeneratorServiceTest {

    @Autowired
    lateinit var oidService: OIDNodeService
    @Autowired
    lateinit var generatorService: GeneratorService

    @Test
    fun createNewGenerator() {
        val saved = createNode("10.1." + System.currentTimeMillis(), "Test", "Unit Testing")
        val genMD = generatorService.createNewGenerator(saved)
        println(genMD)
        println("bizKey: ${genMD.bizKeyDelimiter} " )
        println("allow updates ${genMD.allowKeyUpdates}")

        val loaded = generatorService.getGeneratorForNode(saved.oid)
        println("loaded: ${loaded}")
    }

    @Test
    fun registerNewOids() {
        val saved = createNode("10.1." + System.currentTimeMillis(), "Test", "Unit Testing")
        val genMD = generatorService.createNewGenerator(saved)
        for (i in 1..10)
            generatorService.generateNewOid(genMD.node.oid, "Test-${i}" )


        val genAfterGeneration = generatorService.getGeneratorForNode(saved.oid)
        println("Generator end result: ${genAfterGeneration}; Nextchild: ${genAfterGeneration!!.get().nextChildSequenceNumber}")
        val children =  oidService.getChildrenOf(saved.oid)
        children.forEach {c -> println(c)}

    }


    private fun createNode(oid: String, bizKey: String, description: String): OIDNode {
        val newNode = OIDNode(oid, bizKey)
        newNode.description = description
        val saved = oidService.registerNewOid(newNode)
        return saved
    }
}