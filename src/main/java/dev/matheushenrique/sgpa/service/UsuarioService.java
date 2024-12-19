package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.dto.AccessToken;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Usuario;

public interface UsuarioService {
    Usuario save(Usuario usuario) throws EntityCreationException;
    AccessToken authenticate(String email, String password) throws EntityNotFoundException;
    Usuario findByEmail(String email);
}
