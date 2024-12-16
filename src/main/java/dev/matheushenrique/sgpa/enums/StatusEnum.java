package dev.matheushenrique.sgpa.enums;

public enum StatusEnum {

    ANDAMENTO("ANDAMENTO"),
    CONCLUIDO("CONCLUIDO"),
    ARQUIVADO("ARQUIVADO"),
    CANCELADO("CANCELADO");
    private final String status;
    StatusEnum(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
