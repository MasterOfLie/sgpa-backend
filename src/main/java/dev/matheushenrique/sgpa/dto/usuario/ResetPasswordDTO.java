package dev.matheushenrique.sgpa.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.valueextraction.Unwrapping;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    @NotEmpty(message = "Digite o email.")
    @Email(payload = Unwrapping.Skip.class)
    private String email;
}
