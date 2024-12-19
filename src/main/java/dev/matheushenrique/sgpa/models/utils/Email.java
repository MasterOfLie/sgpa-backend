package dev.matheushenrique.sgpa.models.utils;

import lombok.Data;

@Data
public class Email {
    private String to;
    private String body;
    private String subject;
    private String fistName;
    private String lastName;
}
