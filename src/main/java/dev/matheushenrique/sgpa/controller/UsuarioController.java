package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.UsuarioDTO;
import dev.matheushenrique.sgpa.mapper.UsuarioMapper;
import dev.matheushenrique.sgpa.models.Usuario;
import dev.matheushenrique.sgpa.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioDTO);
        return ResponseEntity.ok().body(usuarioService.save(usuario));
    }

}
