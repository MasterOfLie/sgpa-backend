package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.component.UsuarioAuthenticated;
import dev.matheushenrique.sgpa.dto.perfil.PerfilPermissionDTO;
import dev.matheushenrique.sgpa.enums.PermissionEnum;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Perfil;
import dev.matheushenrique.sgpa.repository.PerfilRepository;
import dev.matheushenrique.sgpa.service.PerfilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioAuthenticated usuarioAuthenticated;

    @Override
    public Perfil createPerfil(Perfil perfil) throws EntityCreationException {
        if(perfilRepository.existsByName((perfil.getName()))){
            throw new EntityCreationException("Já existe um perfil com o nome " + perfil.getName());
        };
        perfilRepository.save(perfil);
        log.info("Serviço {} com ID: {} foi criado com sucesso pelo usuário '{}'.", perfil.getName(), perfil.getId(), usuarioAuthenticated.getUsuarioAuthenticatedInfo());
        return perfil;
    }

    @Override
    @Transactional
    public Void updatePermissions(PerfilPermissionDTO perfilPermissionDTO) throws EntityNotFoundException {
        Perfil perfil = perfilRepository.findById(perfilPermissionDTO.getIdPerfil()).orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado"));
        if (perfil.getListPermissions().contains(PermissionEnum.valueOf(perfilPermissionDTO.getPermission()))) {
            perfil.getListPermissions().remove(PermissionEnum.valueOf(perfilPermissionDTO.getPermission()));
        }else {
            perfil.getListPermissions().add(PermissionEnum.valueOf(perfilPermissionDTO.getPermission()));
        }
        perfilRepository.save(perfil);
        return null;
    }
}
