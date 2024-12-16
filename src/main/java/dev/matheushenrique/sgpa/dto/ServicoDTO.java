package dev.matheushenrique.sgpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServicoDTO {
    private String id;
    @NotBlank(message = "Não pode esta em branco")
    private String name;
    private String description;
    @NotBlank(message = "Não pode esta em branco")
    private String idSetor;
    @NotBlank(message = "Não pode esta em branco")
    private String idDepartamento;

}
