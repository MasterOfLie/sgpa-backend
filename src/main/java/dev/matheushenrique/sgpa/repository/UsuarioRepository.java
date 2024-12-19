package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    boolean existsByCpfCnpj(String cpfCnpj) ;

    boolean existsByEmail(String email);

    Usuario findByEmail(String email);
}
