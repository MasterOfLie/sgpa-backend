package dev.matheushenrique.sgpa.dto;

import dev.matheushenrique.sgpa.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcessoDTO {

    private String id;
    @NotBlank(message = "Não pode esta em branco")
    private String description;
    @NotNull(message = "Não pode esta em branco")
    private StatusEnum status;
    @NotBlank(message = "Não pode esta em branco")
    private String idSetor;
    @NotBlank(message = "Não pode esta em branco")
    private String idServico;
    @NotBlank(message = "Não pode esta em branco")
    private String idDepartamento;
    @NotBlank(message = "Não pode esta em branco")
    private String idSolicitante;
}
