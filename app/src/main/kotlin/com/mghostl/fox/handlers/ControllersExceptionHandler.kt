package com.mghostl.fox.handlers

import com.mghostl.fox.sms.model.SmsRuException
import com.mghostl.fox.sms.model.SmsUserNotFoundException
import com.mghostl.fox.sms.model.SmsWasSentRecentlyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException

@ControllerAdvice
class ControllersExceptionHandler: ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleException(ex: EntityNotFoundException, request: WebRequest) =
        ResponseEntity(ErrorMessage(ex.message ?: "some Exception"), HttpStatus.NOT_FOUND)

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SmsUserNotFoundException::class)
    fun handleException(ex: SmsUserNotFoundException, request: WebRequest) =
        ResponseEntity(ErrorMessage(ex.message ?: "User not found with this phone"), HttpStatus.CONFLICT)

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SmsWasSentRecentlyException::class)
    fun handleException(ex: SmsWasSentRecentlyException, request: WebRequest) =
        ResponseEntity(ErrorMessage(ex.message ?: "Sms was sent recently"), HttpStatus.FORBIDDEN)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SmsRuException::class)
    fun handleException(ex: SmsRuException, request: WebRequest) =
        ResponseEntity(ErrorMessage(ex.message ?: "some smsRu exception"), HttpStatus.INTERNAL_SERVER_ERROR)
}