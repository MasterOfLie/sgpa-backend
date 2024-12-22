package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.*;
import dev.matheushenrique.sgpa.dto.usuario.AccessToken;
import dev.matheushenrique.sgpa.dto.usuario.AuthencicationDTO;
import dev.matheushenrique.sgpa.dto.usuario.ResetPasswordDTO;
import dev.matheushenrique.sgpa.dto.usuario.UsuarioDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.mapper.UsuarioMapper;
import dev.matheushenrique.sgpa.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UsuarioMapper usuarioMapper;
    private final UsuarioService usuarioService;
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthencicationDTO authencicationDTO, HttpServletRequest request) {
        try{
            return ResponseEntity.ok().body(usuarioService.authenticateUser(authencicationDTO.getEmail(), authencicationDTO.getPassword(), request));
        }catch(EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsuarioDTO usuarioDTO, HttpServletRequest request) {
        try{
            usuarioService.createUsuario(usuarioMapper.toUsuario(usuarioDTO));
            return ResponseEntity.ok().body(getTokenAfterRegistration(usuarioDTO, request));
        }catch(EntityNotFoundException | EntityCreationException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    @PostMapping("reset")
    public ResponseEntity<?> resetUsuarioPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            usuarioService.resetUsuarioPassword(resetPasswordDTO.getEmail());
            Map<String, String> map = new HashMap<>();
            map.put("resetPassword", "Nova senha enviada para o e-mail. Qualquer dúvida, entre em contato com o suporte.");
            return ResponseEntity.ok().body(map);
        }catch(EntityNotFoundException e){
            ErroResponse error = ErroResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
    private AccessToken getTokenAfterRegistration(UsuarioDTO usuario , HttpServletRequest request) {
        return usuarioService.authenticateUser(usuario.getEmail(), usuario.getPassword() , request);
    }
}
