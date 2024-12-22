package dev.matheushenrique.sgpa.dto.perfil;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PerfilDTO {
    private String id;
    @NotBlank(message = "Informe o nome do perfil")
    private String name;
    private String description;
}
