package dev.matheushenrique.sgpa.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "tb_setor")
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "setor", fetch = FetchType.LAZY)
    private List<Servico> servicos;

    @OneToMany(mappedBy = "setor", fetch = FetchType.LAZY)
    private List<Processo> processos;
}
