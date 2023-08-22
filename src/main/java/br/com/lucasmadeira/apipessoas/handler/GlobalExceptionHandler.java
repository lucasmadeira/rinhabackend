package br.com.lucasmadeira.apipessoas.handler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintEx = (ConstraintViolationException) ex.getCause();
            if (constraintEx.getConstraintName().toLowerCase().contains("uk_apelido")) {
                return buildResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, "O apelido já está em uso.");
            }
        } else if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();

            // Check for null fields
            boolean containsNullFieldError = bindingResult.getAllErrors().stream()
                    .anyMatch(error -> error instanceof FieldError && ((FieldError) error).getField() == null);

            if (containsNullFieldError) {
                return buildResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, "Campo nulo não é permitido.");
            } else {
                List<String> validationErrors = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList());
                String message = String.join("; ", validationErrors);
                return buildResponseEntity(HttpStatus.BAD_REQUEST, message);
            }
        }

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor.");
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}

