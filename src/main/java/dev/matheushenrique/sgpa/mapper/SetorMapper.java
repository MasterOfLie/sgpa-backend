package dev.matheushenrique.sgpa.mapper;

import dev.matheushenrique.sgpa.dto.SetorDTO;
import dev.matheushenrique.sgpa.models.Setor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetorMapper {


    public Setor toSetor(SetorDTO setorDTO) {
        Setor setor = new Setor();
        setor.setId(setorDTO.getId());
        setor.setName(setorDTO.getName());
        setor.setDescription(setorDTO.getDescription());
        return setor;
    }

    public SetorDTO toSetorDTO(Setor setor) {
        SetorDTO setorDTO = new SetorDTO();
        setorDTO.setId(setor.getId());
        setorDTO.setName(setor.getName());
        setorDTO.setDescription(setor.getDescription());
        return setorDTO;
    }

}
