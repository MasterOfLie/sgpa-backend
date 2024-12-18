package dev.matheushenrique.sgpa.service;

import dev.matheushenrique.sgpa.models.utils.Email;

public interface EmailService {
    void sendEmail(Email email);
    void sendEmailHtml(Email email) throws Exception;
}
