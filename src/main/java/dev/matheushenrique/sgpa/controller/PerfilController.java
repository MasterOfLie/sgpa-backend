package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.ErroResponse;
import dev.matheushenrique.sgpa.dto.perfil.PerfilDTO;
import dev.matheushenrique.sgpa.dto.perfil.PerfilPermissionDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.mapper.PerfilMapper;
import dev.matheushenrique.sgpa.service.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;
    private final PerfilMapper perfilMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CRIAR_PERFIL') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createProfile(@Valid @RequestBody PerfilDTO perfilDTO) {
        try {
            return new ResponseEntity<>(perfilMapper.toPerfilDTO(perfilService.createPerfil(perfilMapper.toPerfil(perfilDTO))), HttpStatus.OK);
        } catch (EntityCreationException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @PostMapping("permissao")
    @PreAuthorize("hasAuthority('ROLE_EDITAR_PERFIL') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updatePermissionsForProfile(@Valid @RequestBody PerfilPermissionDTO perfilPermissionDTO) {
        try {
            return new ResponseEntity<>(perfilService.updatePermissions(perfilPermissionDTO), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }

}
