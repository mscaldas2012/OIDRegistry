package edu.msc.oidRegistry

import edu.msc.oidRegistry.domain.ConflictException
import edu.msc.oidRegistry.domain.DataNotFoundException
import edu.msc.oidRegistry.model.ErrorMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

/**
 *
 *
 * @Created - 12/28/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */

@ControllerAdvice
class RestResponseExceptionHandler @Autowired constructor(var messageSource: MessageSource) {

    @ExceptionHandler(DataNotFoundException::class)
    fun  handleNotFound(ex: RuntimeException , request: HttpServletRequest): ResponseEntity<Any>{
        val error =  ErrorMessage(HttpStatus.NOT_FOUND.value(), request.requestURL.toString())
        error.description = ex.message.toString()
        error.exception = ex.javaClass.name
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ConflictException::class)
    fun handleConflict(ex: RuntimeException, request: HttpServletRequest): ResponseEntity<Any> {
        val error = ErrorMessage(HttpStatus.CONFLICT.value(), request.requestURL.toString())
        error.description = ex.message.toString()
        error.exception = ex.javaClass.name
        return ResponseEntity(error, HttpStatus.CONFLICT)
    }
}