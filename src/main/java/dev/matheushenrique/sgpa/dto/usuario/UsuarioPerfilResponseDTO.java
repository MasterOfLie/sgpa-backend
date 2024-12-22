package dev.matheushenrique.sgpa.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioPerfilResponseDTO {

    private String usuarioName;
    private String perfilName;
}
