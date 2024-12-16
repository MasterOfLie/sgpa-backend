package dev.matheushenrique.sgpa.dto;

import dev.matheushenrique.sgpa.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcessoResponseDTO {

    private String id;
    private String description;
    private StatusEnum status;
    private String idSetor;
    private String setor;
    private String idServico;
    private String Servico;
    private String idDepartamento;
    private String departamento;

    public ProcessoResponseDTO() {
    }

    public ProcessoResponseDTO(String id, String description, StatusEnum status, String idSetor, String setor, String idServico, String servico, String idDepartamento, String departamento) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.idSetor = idSetor;
        this.setor = setor;
        this.idServico = idServico;
        Servico = servico;
        this.idDepartamento = idDepartamento;
        this.departamento = departamento;
    }

}
