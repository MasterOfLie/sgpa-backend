package dev.matheushenrique.sgpa.models;

import dev.matheushenrique.sgpa.enums.PermissionEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tb_perfil")
@Data
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<PermissionEnum> listPermissions;

}
