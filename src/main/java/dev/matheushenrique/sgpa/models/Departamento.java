package dev.matheushenrique.sgpa.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tb_departamento")
@Data
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<Servico> servicos;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<Processo> processos;

    @ManyToMany(mappedBy = "departamentos", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
}
