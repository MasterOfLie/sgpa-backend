package dev.matheushenrique.sgpa.enums;

import lombok.Data;


public enum PermissionEnum {

    CRIAR_PROCESSO("ROLE_CRIAR_PROCESSO"),
    CRIAR_DEPARTAMENTO("ROLE_CRIAR_DEPARTAMENTO"),
    CRIAR_SERTOR("ROLE_CRIAR_SERTOR"),
    CRIAR_SERVICO("ROLE_CRIAR_SERVICO"),
    CRIAR_PERFIL("ROLE_CRIAR_PERFIL");
    private final String permission;

    PermissionEnum(String permission) {
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }
}
