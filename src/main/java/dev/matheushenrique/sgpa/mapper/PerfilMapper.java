package dev.matheushenrique.sgpa.mapper;

import dev.matheushenrique.sgpa.dto.perfil.PerfilDTO;
import dev.matheushenrique.sgpa.models.Perfil;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {

    public Perfil toPerfil(PerfilDTO perfilDTO) {
        Perfil perfil = new Perfil();
        perfil.setId(perfilDTO.getId());
        perfil.setName(perfilDTO.getName());
        perfil.setDescription(perfilDTO.getDescription());
        return perfil;
    }
    public PerfilDTO toPerfilDTO(Perfil perfil) {
        PerfilDTO perfilDTO = new PerfilDTO();
        perfilDTO.setId(perfil.getId());
        perfilDTO.setName(perfil.getName());
        perfilDTO.setDescription(perfil.getDescription());
        return perfilDTO;
    }

}
