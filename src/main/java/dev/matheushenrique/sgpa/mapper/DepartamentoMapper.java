package dev.matheushenrique.sgpa.mapper;

import dev.matheushenrique.sgpa.dto.DepartamentoDTO;
import dev.matheushenrique.sgpa.dto.SetorDTO;
import dev.matheushenrique.sgpa.models.Departamento;
import dev.matheushenrique.sgpa.models.Setor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartamentoMapper {


    public DepartamentoDTO toDepartamentoDTO(Departamento departamento) {
        DepartamentoDTO departamentoDTO = new DepartamentoDTO();
        departamentoDTO.setId(departamento.getId());
        departamentoDTO.setName(departamento.getName());
        departamentoDTO.setDescription(departamento.getDescription());
        return departamentoDTO;
    }
    public Departamento toDepartamento(DepartamentoDTO departamentoDTO) {
        Departamento departamento = new Departamento();
        departamento.setId(departamentoDTO.getId());
        departamento.setName(departamentoDTO.getName());
        departamento.setDescription(departamentoDTO.getDescription());
        return departamento;
    }

}
