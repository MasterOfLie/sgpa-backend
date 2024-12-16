package dev.matheushenrique.sgpa.dto;

import lombok.Data;

@Data
public class ServicoResponseDTO {
    // DTO DE RESPOSTA DO SERVIÇO, COM INFORMAÇÕES DO SERVIÇO, DEPARTAMENTO E SETOR LOCALIZADOS
    private String id;
    private String name;
    private String description;
    private String setor;
    private String idSetor;
    private String departamento;
    private String idDepartamento;
    public ServicoResponseDTO() {}
    public ServicoResponseDTO(String id, String name, String description, String setor, String idSetor, String departamento, String idDepartamento) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.setor = setor;
        this.idSetor = idSetor;
        this.departamento = departamento;
        this.idDepartamento = idDepartamento;
    }


}
