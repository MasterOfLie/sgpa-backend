package dev.matheushenrique.sgpa.dto.usuario;

import lombok.Data;

@Data
public class AuthencicationDTO {
    private String email;
    private String password;
}
