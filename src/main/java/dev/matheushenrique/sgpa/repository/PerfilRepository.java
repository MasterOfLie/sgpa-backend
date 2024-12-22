package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, String> {
    boolean existsByName(String name);
}
