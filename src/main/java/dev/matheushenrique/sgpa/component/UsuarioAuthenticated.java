package dev.matheushenrique.sgpa.component;

import dev.matheushenrique.sgpa.models.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAuthenticated {

    public Usuario getUsuarioAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return (Usuario) authentication.getPrincipal();
        }
        throw new RuntimeException("Usuário não autenticado");
    }

    /**
     * INFORMAÇÕES DO USUARIO AUTENTICADO
     * @return NOME: MATHEUS EMAIL: SGPA@SGPA.COM ID: UUID
     */
    public String getUsuarioAuthenticatedInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            return "Nome: " + usuario.getFirstName() + " " + usuario.getLastName() + " Email " + usuario.getEmail() + " Id: " + usuario.getId();
        }
        throw new RuntimeException("Usuário não autenticado");
    }

}
