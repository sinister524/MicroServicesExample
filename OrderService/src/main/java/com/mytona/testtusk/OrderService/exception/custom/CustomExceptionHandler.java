

package com.mytona.testtusk.OrderService.exception.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.mytona.testtusk.OrderService.exception.BadRequestException;
import com.mytona.testtusk.OrderService.exception.PermissionException;
import com.mytona.testtusk.OrderService.exception.ResourceNotFoundException;
import com.mytona.testtusk.OrderService.exception.UnauthorizedException;
import com.mytona.testtusk.OrderService.pojo.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    protected CustomMessage handleUnauthorizedException(final UnauthorizedException ex) {
        return new CustomMessage(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({PermissionException.class})
    protected CustomMessage handleForbiddenException(final PermissionException ex) {
        return new CustomMessage(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    protected CustomMessage handleBadRequestException(final BadRequestException ex) {
        return new CustomMessage(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    protected CustomMessage handleNotFoundException(final ResourceNotFoundException ex) {
        return new CustomMessage(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected CustomMessage handleValidationException(final MethodArgumentNotValidException ex) {
        final BindingResult result = ex.getBindingResult();
        final FieldError error = result.getFieldError();
        try {
            final String msg = error.getDefaultMessage();
            return new CustomMessage(msg);
        } catch (NoSuchMessageException e) {
            String fieldName = error.getField();
            try {
                final Field field = result.getTarget().getClass().getDeclaredField(fieldName);
                if (field.isAnnotationPresent((Class<? extends Annotation>) JsonProperty.class)) {
                    fieldName = field.getAnnotation(JsonProperty.class).value();
                }
            } catch (NoSuchFieldException ex2) {
            }
            return new CustomMessage(fieldName + " - " + error.getDefaultMessage());
        }
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    protected CustomMessage handleMaxUploadSizeException(final MaxUploadSizeExceededException ex) {
        return new CustomMessage("exception.max.upload.size");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({JsonParseException.class})
    protected CustomMessage handleJsonParseException(final JsonParseException ex) {
        return new CustomMessage(ex.getMessage());
    }
}
