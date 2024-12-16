package dev.matheushenrique.sgpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetorDTO {

    private String id;
    @NotBlank(message = "Nome não pode estar em branco")
    private String name;
    private String description;

}
