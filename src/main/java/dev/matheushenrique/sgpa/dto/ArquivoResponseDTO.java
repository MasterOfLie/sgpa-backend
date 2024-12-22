package dev.matheushenrique.sgpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoResponseDTO {

    private String id;
    private String name;
    private String usuarioUpload;

}
