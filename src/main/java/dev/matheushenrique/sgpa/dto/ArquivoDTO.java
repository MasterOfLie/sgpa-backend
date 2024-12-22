package dev.matheushenrique.sgpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArquivoDTO {

    @NotBlank(message = "Informe o id do processo.")
    private String idProcesso;

}
