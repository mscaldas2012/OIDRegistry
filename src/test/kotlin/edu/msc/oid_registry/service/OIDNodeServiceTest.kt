package edu.msc.oid_registry.service

import edu.msc.oid_registry.model.OIDNode
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @Created - 12/24/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class OIDNodeServiceTest {

    @Autowired
    lateinit var service: OIDNodeService

    val emojis = mapOf(
            ":-)" to "Smiley",
            ":-D" to "Kaugthing",
            ":v" to "Unenthused",
            ":-))" to "Very Happy",
            ":'-(" to "Crying",
            ":-O" to "Suprise",
            ":-*" to "Kiss",
            ";-)" to "Wink")


    private fun createNode(oid: String, bizKey: String, description: String):OIDNode {
        val newNode = OIDNode(oid, bizKey)
        newNode.description = description
        val saved = service.registerNewOid(newNode);
        return saved
    }

    private fun registerAlphabetOids(prefix: String) {
        for (i in (1..26)) {
            val character = (i+64).toChar() + ""
            createNode(prefix + i, character, character)
        }
    }

    private fun registerEmoticons(prefix: String) {
        var i = 1
        emojis.forEach { key, value -> createNode(prefix + i++, key, value)}
    }

    @Test
    fun registerNewOid() {

        val saved = createNode("5.1." + System.currentTimeMillis(), "Test", "Unit Testing")
        assert(saved != null)
        assert(saved.oid.startsWith("5.1"))
        assert(saved.description.equals("Unit Testing"))
        println(saved)
        println(saved.description)
    }

    @Test
    fun getNode() {
        val newOid = "5.1." +  + System.currentTimeMillis()
        val saved = createNode(newOid, "Test", "Unit Testing")
        val test = service.getNode(newOid)
        println(test)
    }

    @Test
    fun updateNode() {
        val newOid = "5.1." +  + System.currentTimeMillis()
        val saved = createNode(newOid, "Test", "Unit Testing")
        val test = service.getNode(newOid)?.get()
        test?.description = "Unit Test updated"
        val updated = service.updateNode(test!!)
        println(updated.description)

    }

    @Test
    fun deleteNode() {
        val newOid = "5.1." +  + System.currentTimeMillis()
        val saved = createNode(newOid, "Test", "Unit Testing")
        val test = service.getNode(newOid)
        assert(test?.isPresent == true)
        assertNotNull(test)
        service.deleteNode(newOid)
        val deleted = service.getNode(newOid)
        assert(deleted?.isPresent == false)

    }

    @Test
    fun getChildrenOf() {
        registerEmoticons("6.2.")
        registerAlphabetOids("6.1.")
        val emojis: List<OIDNode> = service.getChildrenOf("6.1")
        emojis.forEach{ n -> println(n) }
    }

    @Test
    fun getNodesByBizKey() {
        registerAlphabetOids("6.1.")
        val letterC = service.getNodesByBizKey("C")
        println(letterC)
    }

    @Test
    fun getChildrenNodeByBizKey() {
        registerAlphabetOids("6.1.")
        val letterT = service.getChildrenNodeByBizKey("6.1", "T")
        println(letterT)
    }
}