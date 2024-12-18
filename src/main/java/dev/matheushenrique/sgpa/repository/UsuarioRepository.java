package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
