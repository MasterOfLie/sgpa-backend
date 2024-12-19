package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.AccessToken;
import dev.matheushenrique.sgpa.dto.AuthencicationDTO;
import dev.matheushenrique.sgpa.dto.ErroResponse;
import dev.matheushenrique.sgpa.dto.UsuarioDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.mapper.UsuarioMapper;
import dev.matheushenrique.sgpa.models.Usuario;
import dev.matheushenrique.sgpa.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UsuarioMapper usuarioMapper;
    private final UsuarioService usuarioService;
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthencicationDTO authencicationDTO) {
        try{
            return ResponseEntity.ok().body(usuarioService.authenticate(authencicationDTO.getEmail(), authencicationDTO.getPassword()));
        }catch(EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioDTO);
        try{
            Usuario usuarioSalvo = usuarioService.save(usuario);
            AccessToken accessToken = usuarioService.authenticate(usuarioSalvo.getEmail(), usuarioDTO.getPassword());
            return ResponseEntity.ok().body(accessToken);
        }catch(EntityNotFoundException | EntityCreationException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
}
