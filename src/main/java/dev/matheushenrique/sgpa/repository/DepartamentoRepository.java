package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.models.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartamentoRepository extends JpaRepository<Departamento, String> {
    boolean existsByName(String name);

    Departamento findByName(String name);
}
