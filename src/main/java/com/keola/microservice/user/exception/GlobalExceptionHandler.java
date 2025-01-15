package com.keola.microservice.user.exception;
import com.keola.microservice.user.exception.util.ErrorUtils;
import com.keola.microservice.user.models.ApiError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public Mono<ResponseEntity<ApiError>> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = ApiError.builder()
                .errorCode(HttpStatus.NOT_FOUND)
                .errorMessage(ex.getMessage())
                .detail("The requested customer could not be found in the system.")
                .build();

        return Mono.just(ResponseEntity
                .status(apiError.getErrorCode())
                .body(apiError));
    }
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ApiError>> handleValidationExceptions(WebExchangeBindException ex) {
        // Obtener los errores de validación
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        // Crear el objeto de error con los mensajes
        ApiError apiError = ApiError.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .detail("Validation failed review body")
                .errorMessage("Validation failed")
                .listErrorValidation(errorMessages)
                .build();

        // Retornar la respuesta con los detalles del error
        return Mono.just(ResponseEntity
                .status(apiError.getErrorCode())
                .body(apiError));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Mono<ResponseEntity<ApiError>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Delegar la lógica al utilitario ErrorUtils
        ApiError apiError = ErrorUtils.handleDataIntegrityViolationException(ex);
        return Mono.just(ResponseEntity
                .status(apiError.getErrorCode())
                .body(apiError));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiError>> handleGenericException(Exception ex) {
        ApiError apiError = ApiError.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage("An unexpected error occurred.")
                .detail(ex.getMessage())
                .build();

        return Mono.just(ResponseEntity
                .status(apiError.getErrorCode())
                .body(apiError));
    }

}
