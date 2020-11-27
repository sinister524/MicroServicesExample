package com.mytona.testtusk.OrderService.exception;

import com.mytona.testtusk.OrderService.pojo.CustomMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class RestExceptionHandler
//        extends ResponseEntityExceptionHandler
{

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({OrderException.class})
//    protected CustomMessage handOrderException (final OrderException exception) {
//        return new CustomMessage(exception.getMessage());
//    }
}
