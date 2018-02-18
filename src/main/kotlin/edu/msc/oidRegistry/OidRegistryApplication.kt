package edu.msc.oidRegistry

import org.h2.server.web.WebServlet
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport




@SpringBootApplication
class OidRegistryApplication

fun main(args: Array<String>) {
    runApplication<OidRegistryApplication>(*args)
}

@Configuration
class WebMvcConfig : WebMvcConfigurationSupport() {

    @Bean
    fun h2servletRegistration(): ServletRegistrationBean<*> {
        val registrationBean = ServletRegistrationBean(WebServlet())
        registrationBean.addUrlMappings("/console/*")
        registrationBean.addInitParameter("webAllowOthers", "true")

        return registrationBean
    }
}
