package dev.matheushenrique.sgpa.models.utils;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationConfig {
    @Id
    private String settingsKey;
    private String value;

}
