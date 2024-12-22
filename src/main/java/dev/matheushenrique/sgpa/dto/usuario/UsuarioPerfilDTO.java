package dev.matheushenrique.sgpa.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioPerfilDTO {

    @NotBlank(message = "Informe o id do usuario")
    private String idUsuario;
    @NotBlank(message = "Informe o id do perfil")
    private String idPerfil;
}
