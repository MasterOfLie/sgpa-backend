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
    private String servico;
    private String idDepartamento;
    private String departamento;
    private Long protocolo;
    private String anoVigencia;
    private String solicitante;

    public ProcessoResponseDTO() {
    }

    public ProcessoResponseDTO(String id, String description, StatusEnum status, String idSetor, String setor, String idServico, String servico, String idDepartamento, String departamento, Long protocolo, String anoVigencia, String solicitante) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.idSetor = idSetor;
        this.setor = setor;
        this.idServico = idServico;
        this.servico = servico;
        this.idDepartamento = idDepartamento;
        this.departamento = departamento;
        this.protocolo = protocolo;
        this.anoVigencia = anoVigencia;
        this.solicitante = solicitante;
    }

}
