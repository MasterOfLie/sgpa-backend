package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.models.utils.Email;

public interface EmailService {
    void sendSimplePasswordRest(Email email, String password);
    void sendEmailWelcome(Email email);
}
