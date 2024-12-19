package dev.matheushenrique.sgpa.controller;

import dev.matheushenrique.sgpa.dto.ApplicationConfigDTO;
import dev.matheushenrique.sgpa.service.ApplicationConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationConfigService applicationConfigService;


    @PostMapping
    public ResponseEntity<?> configApplication(@Valid @RequestBody ApplicationConfigDTO application) {
        applicationConfigService.save(application);
        return ResponseEntity.ok(application);
    }
}
