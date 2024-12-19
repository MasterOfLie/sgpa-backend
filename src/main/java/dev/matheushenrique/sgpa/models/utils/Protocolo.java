package dev.matheushenrique.sgpa.models.utils;

import dev.matheushenrique.sgpa.models.Processo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tb_protocolo")
@Data
public class Protocolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String anoVigencia = DateTimeFormatter.ofPattern("yyyy").format(LocalDateTime.now());

    @OneToOne(mappedBy = "protocolo")
    private Processo processo;
}
