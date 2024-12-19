package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.models.utils.Email;

public interface EmailService {
    void sendSimpleEmail(Email email);
    void sendEmail(Email email);
}
