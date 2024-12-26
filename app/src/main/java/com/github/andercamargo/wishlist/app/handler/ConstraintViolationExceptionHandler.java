package com.github.andercamargo.wishlist.app.handler;

import com.github.andercamargo.wishlist.app.model.Constraint;
import com.github.andercamargo.wishlist.domain.dto.ResponseDTO;
import com.github.andercamargo.wishlist.domain.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedList;

import static com.github.andercamargo.wishlist.domain.enumeration.ResponseMessageApiEnum.OPERACAO_ERRO;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler ({jakarta.validation.ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseDTO handle(
            jakarta.validation.ConstraintViolationException e) {

        var constraints = new LinkedList<Constraint>();

        for (var constraint = e.getConstraintViolations().stream().iterator(); constraint.hasNext(); ) {
            constraints.add(new Constraint(constraint.next().getMessage()));
        }

        return new ResponseService().covertResponse(BAD_REQUEST.value(),
                OPERACAO_ERRO.getValor(), constraints);
    }
}
