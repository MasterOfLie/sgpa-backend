package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.dto.ApplicationConfigDTO;
import dev.matheushenrique.sgpa.models.utils.ApplicationConfig;

public interface ApplicationConfigService {
    void save(ApplicationConfigDTO applicationConfig);
    ApplicationConfig getApplicationName(String settingsKey);
    ApplicationConfig getOrganizationName(String settingsKey);
}
