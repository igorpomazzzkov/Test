package com.ratekino.exception

import javassist.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    fun notFoundExceptionHandler(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun jsonParseExceptionHandler(
        ex: HttpMessageNotReadableException,
        request: WebRequest
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            Date(),
            "Неверый запрос от клиента",
            request.getDescription(false)
        )
        return ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(
        ex: IllegalArgumentException,
        request: WebRequest
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST)
    }
}