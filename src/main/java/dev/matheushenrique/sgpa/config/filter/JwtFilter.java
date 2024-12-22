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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
                String emailToken = jwtService.getEmailFromToken(token);
                Usuario usuario = usuarioService.getUsuarioByEmail(emailToken);
                setUserAsAuthenticated(usuario);
            }catch (InvalidTokenException e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token inválido ou expirado.\"}");
                return;
            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Erro na validação do token. Por favor, faça login novamente ou entre em contato com o administrador do sistema.\"}");
                log.error("Erro na verificação do token: "+ e.getMessage());
                return;
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
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
