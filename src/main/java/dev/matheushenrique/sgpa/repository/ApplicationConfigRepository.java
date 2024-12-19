package dev.matheushenrique.sgpa.repository;

import dev.matheushenrique.sgpa.models.utils.ApplicationConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationConfigRepository extends JpaRepository<ApplicationConfig, String> {
}
