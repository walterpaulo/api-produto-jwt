package com.walterpaulo.produto.erros;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.walterpaulo.produto.controllers.Response;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Response<?> message = new Response<>();
        message.getErros().add(ex.getMessage());
        message.setStatus(status.value());
        return new ResponseEntity<>(message, status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleNoSuchElementException(RuntimeException ex) {
        Response<?> message = new Response<>();
        message.getErros().add(ex.getMessage());
        message.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Response<?> message = new Response<>();
        message.getErros().add(ex.getMessage());
        message.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}
