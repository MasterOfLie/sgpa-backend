package dev.matheushenrique.sgpa.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResponse(int status, String message, List<ErroCampo> erros) {

    public static ErroResponse defaultResponse(String message) {
        return new ErroResponse(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }
    public static ErroResponse conflictResponse(String message) {
        return new ErroResponse(HttpStatus.CONFLICT.value(), message, List.of());
    }
    public static ErroResponse validationResponse(String message, List<ErroCampo> erros) {
        return new ErroResponse(HttpStatus.BAD_REQUEST.value(), message, erros);
    }
}
