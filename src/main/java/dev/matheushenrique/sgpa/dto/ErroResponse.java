package dev.matheushenrique.sgpa.dto;
import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResponse(int status, String messagem, List<ErroCampo> erros) {

    public static ErroResponse defaultResponse(String messagem) {
        return new ErroResponse(HttpStatus.BAD_REQUEST.value(), messagem, List.of());
    }
    public static ErroResponse conflictResponse(String messagem) {
        return new ErroResponse(HttpStatus.CONFLICT.value(), messagem, List.of());
    }
    public static ErroResponse validationResponse(String messagem, List<ErroCampo> erros) {
        return new ErroResponse(HttpStatus.BAD_REQUEST.value(), messagem, erros);
    }
    public static ErroResponse unauthorizedResponse(String messagem) {
        return new ErroResponse(HttpStatus.FORBIDDEN.value(), messagem, List.of());
    }
}
