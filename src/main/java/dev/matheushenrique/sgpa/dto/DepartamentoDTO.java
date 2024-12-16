package dev.matheushenrique.sgpa.dto;

import dev.matheushenrique.sgpa.models.Departamento;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartamentoDTO {

    private String id;
    @NotBlank(message = "Nome não pode estar em branco")
    private String name;
    private String description;



}
