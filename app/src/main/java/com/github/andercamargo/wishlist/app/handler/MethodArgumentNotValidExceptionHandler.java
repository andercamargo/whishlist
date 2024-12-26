package com.github.andercamargo.wishlist.app.handler;

import com.github.andercamargo.wishlist.app.model.Validation;
import com.github.andercamargo.wishlist.domain.dto.ResponseDTO;
import com.github.andercamargo.wishlist.domain.service.ResponseService;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.core.Ordered;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.github.andercamargo.wishlist.domain.enumeration.ResponseMessageApiEnum.OPERACAO_ERRO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private ResponseDTO processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        Validation error = new Validation();

        for (var fieldError: fieldErrors) {
            error.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseService().covertResponse(BAD_REQUEST.value(), OPERACAO_ERRO.getValor(), error);

    }
}