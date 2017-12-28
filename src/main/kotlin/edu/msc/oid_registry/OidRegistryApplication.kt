package edu.msc.oid_registry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OidRegistryApplication

fun main(args: Array<String>) {
    runApplication<OidRegistryApplication>(*args)
}
