package dev.matheushenrique.sgpa.config.filter;

import dev.matheushenrique.sgpa.config.jwt.JwtService;
import dev.matheushenrique.sgpa.exception.InvalidTokenException;
import dev.matheushenrique.sgpa.models.Usuario;
import dev.matheushenrique.sgpa.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);
        if (token != null){
            try {
                String emailToken = jwtService.getIdFromToken(token);
                Usuario usuario = usuarioService.findByEmail(emailToken);
                setUserAsAuthenticated(usuario);
            }catch (InvalidTokenException e){
                log.error("Token invalido: " + e.getMessage());
            }catch (Exception e){
                log.error("Erro na verificação do token: "+ e.getMessage());
            }

        }

        filterChain.doFilter(request, response);

    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String[] tokens = token.split(" ", 2);
            if (tokens.length == 2) {
                return tokens[1];
            }
        }
        return null;
    }
    private void setUserAsAuthenticated(Usuario usuario) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
