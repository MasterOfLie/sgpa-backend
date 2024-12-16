package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.models.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetorRepository extends JpaRepository<Setor, String> {

    Boolean existsByName(String name);
    Setor findByName(String name);
}
