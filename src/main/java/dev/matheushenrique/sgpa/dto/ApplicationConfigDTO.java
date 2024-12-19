package dev.matheushenrique.sgpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationConfigDTO {
    @NotBlank(message = "Porfavor informe o nome da aplicação")
    private String appName;
    @NotBlank(message = "Porfavor informe o nome da organização")
    private String organizationName;
}
