package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.*;
import dev.matheushenrique.sgpa.dto.usuario.UsuarioDTO;
import dev.matheushenrique.sgpa.dto.usuario.UsuarioPerfilDTO;
import dev.matheushenrique.sgpa.dto.usuario.UsuarioPerfilResponseDTO;
import dev.matheushenrique.sgpa.dto.usuario.UsuarioResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.mapper.UsuarioMapper;
import dev.matheushenrique.sgpa.models.Usuario;
import dev.matheushenrique.sgpa.repository.PerfilRepository;
import dev.matheushenrique.sgpa.repository.UsuarioRepository;
import dev.matheushenrique.sgpa.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioDTO);
        try {
            return ResponseEntity.ok().body(usuarioService.createUsuario(usuario));
        } catch (EntityCreationException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllUsuarios() {
        List<UsuarioResponseDTO> usuarioResponseDTOList = usuarioService.getAllUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDTOList);
    }

    @PostMapping("perfil")
    @PreAuthorize("hasAuthority('ROLE_ATRIBUIR_PERFIL') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addPerfilToUsuario(@Valid @RequestBody UsuarioPerfilDTO usuarioPerfilDTO) {
        try {
            UsuarioPerfilResponseDTO responseDTO = usuarioMapper.toUsuarioPerfilResponseDTO(usuarioService.addPerfilToUsuario(usuarioPerfilDTO.getIdUsuario(), usuarioPerfilDTO.getIdPerfil()));
            return ResponseEntity.ok().body(responseDTO);
        } catch (EntityNotFoundException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
}
