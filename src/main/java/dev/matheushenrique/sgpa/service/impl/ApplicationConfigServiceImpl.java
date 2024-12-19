package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.dto.ApplicationConfigDTO;
import dev.matheushenrique.sgpa.exception.EntityNotFoundException;
import dev.matheushenrique.sgpa.models.utils.ApplicationConfig;
import dev.matheushenrique.sgpa.repository.ApplicationConfigRepository;
import dev.matheushenrique.sgpa.service.ApplicationConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationConfigServiceImpl implements ApplicationConfigService {

    private final ApplicationConfigRepository applicationConfigRepository;

    @Override
    public void save(ApplicationConfigDTO applicationConfig) {
        applicationConfigRepository.save(new ApplicationConfig("settings::app:Name", applicationConfig.getAppName()));
        applicationConfigRepository.save(new ApplicationConfig("settings::app:organizationName", applicationConfig.getOrganizationName()));
    }

    @Override
    public ApplicationConfig getApplicationName(String settingsKey) throws EntityNotFoundException {
        if (keyHasValue(settingsKey)) {
            return applicationConfigRepository.findById(settingsKey).orElseThrow();
        }
        return new ApplicationConfig("", "Sistema de Gestão de Processos Administrativos");
    }

    @Override
    public ApplicationConfig getOrganizationName(String settingsKey) throws EntityNotFoundException {
        if (keyHasValue(settingsKey)) {
            return applicationConfigRepository.findById(settingsKey).orElseThrow();
        }
        return new ApplicationConfig("", "SPGA Administrativos");
    }


    private boolean keyHasValue(String string){
        return applicationConfigRepository.existsById(string);
    }
}
