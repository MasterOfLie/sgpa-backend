package dev.matheushenrique.sgpa.exception;

import dev.matheushenrique.sgpa.dto.ErroCampo;
import dev.matheushenrique.sgpa.dto.ErroResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErroCampo> erroCampos = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(erroCampo -> {
            erroCampos.add(new ErroCampo(erroCampo.getField(), erroCampo.getDefaultMessage()));
        });
        ErroResponse errors = ErroResponse.validationResponse("Erro de Validação", erroCampos);
        return ResponseEntity.status(errors.status()).body(errors);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ErroResponse errors = ErroResponse.defaultResponse("Erro de integridade no banco de dados: " + ex.getCause().getMessage());
        return ResponseEntity.status(errors.status()).body(errors);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErroResponse errors = ErroResponse.defaultResponse("Erro no envio das informações: " + ex.getCause().getMessage());
        return ResponseEntity.status(errors.status()).body(errors);
    }
    @ExceptionHandler(AccessDeniedException.class) // RESPOSTA PARA O PreAuthorize (stackoverflow) :d
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        ErroResponse errors = ErroResponse.unauthorizedResponse("Você não tem permissão para acessar este recurso.");
        return ResponseEntity.status(errors.status()).body(errors);
    }
}
