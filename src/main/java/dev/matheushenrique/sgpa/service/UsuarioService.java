package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.dto.usuario.AccessToken;
import dev.matheushenrique.sgpa.dto.usuario.UsuarioResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Usuario;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UsuarioService {
    Usuario createUsuario(Usuario usuario) throws EntityCreationException;
    AccessToken authenticateUser(String email, String password, HttpServletRequest request) throws EntityNotFoundException;
    Usuario getUsuarioByEmail(String email);
    List<UsuarioResponseDTO> getAllUsuarios();
    void resetUsuarioPassword(String email) throws EntityNotFoundException;
    Usuario addPerfilToUsuario(String idUsuario, String idPerfil) throws EntityNotFoundException;
}
