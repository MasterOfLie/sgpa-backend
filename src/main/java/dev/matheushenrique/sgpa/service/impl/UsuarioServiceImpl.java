package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.exception.EntityCreationException;
import dev.matheushenrique.sgpa.models.Usuario;
import dev.matheushenrique.sgpa.models.utils.Email;
import dev.matheushenrique.sgpa.repository.UsuarioRepository;
import dev.matheushenrique.sgpa.service.EmailService;
import dev.matheushenrique.sgpa.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    @Override
    public Usuario save(Usuario usuario){
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        Email email = new Email();
        email.setTo(usuarioSalvo.getEmail());
        email.setSubject("Bem vindo ao SGPA");
        email.setFistName(usuarioSalvo.getFistName());
        email.setLastName(usuarioSalvo.getLastName());
        try {
            emailService.sendEmailHtml(email);
        }catch (Exception e){
            return usuarioSalvo;
        }
        return usuarioSalvo;
    }
}
