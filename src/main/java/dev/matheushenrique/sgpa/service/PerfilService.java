package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.dto.perfil.PerfilPermissionDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Perfil;

public interface PerfilService {
    Perfil createPerfil(Perfil perfil) throws EntityCreationException;
    Void updatePermissions(PerfilPermissionDTO perfilPermissionDTO) throws EntityNotFoundException;

}
