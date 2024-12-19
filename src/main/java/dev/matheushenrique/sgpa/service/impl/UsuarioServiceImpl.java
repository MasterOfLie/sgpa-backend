package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.config.jwt.JwtService;
import dev.matheushenrique.sgpa.dto.AccessToken;
import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.Usuario;
import dev.matheushenrique.sgpa.models.utils.Email;
import dev.matheushenrique.sgpa.repository.UsuarioRepository;
import dev.matheushenrique.sgpa.service.EmailService;
import dev.matheushenrique.sgpa.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public Usuario save(Usuario usuario) throws EntityCreationException{
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new EntityCreationException("Usuario já cadastrado com esse email");
        }
        if(usuarioRepository.existsByCpfCnpj(usuario.getCpfCnpj())){
            throw new EntityCreationException("Usuario já cadastrado com CPF/CNPJ");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        Email email = new Email();
        email.setTo(usuarioSalvo.getEmail());
        email.setFistName(usuarioSalvo.getFirstName());
        email.setLastName(usuarioSalvo.getLastName());
        email.setSubject("CADASTRO COM SUCESSO");
        emailService.sendEmail(email);
        return usuarioSalvo;
    }

    @Override
    public AccessToken authenticate(String email, String password) throws EntityNotFoundException {
       Usuario usuario = usuarioRepository.findByEmail(email);
       if(usuario == null){
           throw new EntityNotFoundException("Usuario nao encontrado");
       }
       if(passwordEncoder.matches(password, usuario.getPassword())){
           return jwtService.getAccessToken(usuario);
       }
       throw  new EntityNotFoundException("Email ou Senha incorreta") ;
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
