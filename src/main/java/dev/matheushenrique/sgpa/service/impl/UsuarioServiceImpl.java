package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.component.RequestInfo;
import dev.matheushenrique.sgpa.component.UsuarioAuthenticated;
import dev.matheushenrique.sgpa.config.jwt.JwtService;
import dev.matheushenrique.sgpa.dto.usuario.AccessToken;
import dev.matheushenrique.sgpa.dto.usuario.UsuarioResponseDTO;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityErrorException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Perfil;
import dev.matheushenrique.sgpa.models.Usuario;
import dev.matheushenrique.sgpa.models.utils.Email;
import dev.matheushenrique.sgpa.repository.PerfilRepository;
import dev.matheushenrique.sgpa.repository.UsuarioRepository;
import dev.matheushenrique.sgpa.service.EmailService;
import dev.matheushenrique.sgpa.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.security.SecureRandom;


@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RequestInfo requestInfo;
    private final PerfilRepository perfilRepository;
    private final UsuarioAuthenticated usuarioAuthenticated;

    @Override
    public Usuario createUsuario(Usuario usuario) throws EntityCreationException {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new EntityCreationException("Usuario já cadastrado com esse email");
        }
        if (usuarioRepository.existsByCpfCnpj(usuario.getCpfCnpj())) {
            throw new EntityCreationException("Usuario já cadastrado com CPF/CNPJ");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        Email email = new Email();
        email.setTo(usuarioSalvo.getEmail());
        email.setFistName(usuarioSalvo.getFirstName());
        email.setLastName(usuarioSalvo.getLastName());
        email.setSubject("CADASTRO COM SUCESSO");
        emailService.sendEmailWelcome(email);
        return usuarioSalvo;
    }

    @Override
    public AccessToken authenticateUser(String email, String password, HttpServletRequest request) throws EntityNotFoundException {
        Usuario usuario = getUsuario(email);
        if (passwordEncoder.matches(password, usuario.getPassword())) {
            return jwtService.getAccessToken(usuario);
        }
        log.warn("Tentativa de login malsucedida para o usuário '{}'. Endereço IP: '{}'.", email, requestInfo.getClientIp(request));
        throw new EntityNotFoundException("Email ou Senha incorreta");
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<UsuarioResponseDTO> getAllUsuarios() {
        return usuarioRepository.listUsuario();
    }

    @Override
    @Transactional
    public void resetUsuarioPassword(String email) throws EntityNotFoundException {
        String newPassword = getNewPassword();
        Usuario resetPasswordUser = getUsuario(email);
        Email passwordSendEmail = new Email();
        passwordSendEmail.setTo(resetPasswordUser.getEmail());
        passwordSendEmail.setFistName(resetPasswordUser.getFirstName());
        passwordSendEmail.setLastName(resetPasswordUser.getLastName());
        passwordSendEmail.setSubject("SENHA RESETADA");
        emailService.sendSimplePasswordRest(passwordSendEmail, newPassword);
        resetPasswordUser.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepository.save(resetPasswordUser);
        log.info("Senha redefinida com sucesso para o usuário '{}', ID: {} e e-mail: '{}'.",
                resetPasswordUser.getFirstName() + " " + resetPasswordUser.getLastName(),
                resetPasswordUser.getId(),
                resetPasswordUser.getEmail());
    }

    @Override
    @Transactional
    public Usuario addPerfilToUsuario(String idUsuario, String idPerfil) throws EntityNotFoundException {
        Usuario usuario = getUsuarioById(idUsuario);
        usuario.setPerfil(getPerfilById(idPerfil));
        log.info("Perfil {} foi adicionado ao usuário {} (ID: {}) pelo usuário {}.",
                usuario.getPerfil().getName(),
                usuario.getFirstName() + " " + usuario.getLastName(),
                usuario.getId(),
                usuarioAuthenticated.getUsuarioAuthenticatedInfo());
        return usuarioRepository.save(usuario);
    }

    private Perfil getPerfilById(String idPerfil) {
        return (perfilRepository.findById(idPerfil)
                .orElseThrow(() -> new EntityNotFoundException("Perfil com o id " + idPerfil + " não encontrado")));
    }

    private Usuario getUsuarioById(String idUsuario) {
        return (usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario com o id " + idUsuario + " não encontrado")));
    }

    private Usuario getUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuario com o email" + email + " não encontrado");
        }
        return usuario;
    }

    private String getNewPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]};:,<.>?";
        StringBuilder password = new StringBuilder();
        SecureRandom Random = new SecureRandom();
        while (password.length() < 16) {
            int index = Random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
