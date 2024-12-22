package dev.matheushenrique.sgpa.service.impl;

import dev.matheushenrique.sgpa.models.utils.Email;
import dev.matheushenrique.sgpa.service.ApplicationConfigService;
import dev.matheushenrique.sgpa.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final ApplicationConfigService applicationConfigService;

    @Override
    public void sendSimplePasswordRest(Email email, String password) {
        try {
            templateEmailPasswordRest(email, password);
        }catch (Exception e){
            log.error("Foi identificado um erro ao enviar o e-mail. Por favor, verifique os dados do usuário ou o servidor SMTP. LOG: " +  e.getMessage());
        }
    }

    @Override
    public void sendEmailWelcome(Email email) {
        try {
            templateEmailWelcome(email);
        }catch (Exception e){
            log.error("Foi identificado um erro ao enviar o e-mail. Por favor, verifique os dados do usuário ou o servidor SMTP. LOG: " +  e.getMessage());
        }
    }



    private void templateEmailWelcome(Email email) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper emailSender = new MimeMessageHelper(message, true);

        emailSender.setFrom("noreply@matheushenrique.dev.br", applicationConfigService.getApplicationName("settings::app:Name").getValue());
        emailSender.setTo(email.getTo());
        emailSender.setSubject(email.getSubject());

        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"pt-br\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; background-color: #f4f4f4; padding: 20px; }" +
                ".email-container { max-width: 600px; margin: 0 auto; background-color: #fff; border-radius: 8px; padding: 20px; box-shadow: 0 4px 6px rgba(0, 0, 0, .1); }" +
                "h1 { color: #4CAF50; }" +
                "p { font-size: 16px; line-height: 1.5; }" +
                ".button { background-color: #4CAF50; color: #fff; text-align: center; padding: 12px 20px; border-radius: 5px; text-decoration: none; display: inline-block; }" +
                ".footer { text-align: center; font-size: 12px; color: #888; margin-top: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"email-container\">" +
                "<h1>Bem-vindo ao nosso serviço!</h1>" +
                "<p>Olá, " + email.getFistName() + " "+ email.getLastName()+ ",</p>" +
                "<p>Estamos felizes em tê-lo conosco. Aproveite ao máximo os recursos que oferecemos e se precisar de ajuda, nossa equipe de suporte está à disposição.</p>" +
                "<p><a href=\"https://matheushenrique.dev.br\" class=\"button\">Acessar minha conta</a></p>" +
                "<div class=\"footer\"><p>&copy; 2024 SGPA | Todos os direitos reservados.</p></div>" +
                "</div>" +
                "</body>" +
                "</html>";

        emailSender.setText(htmlContent, true);

        mailSender.send(message);
    }
    private void templateEmailPasswordRest(Email email, String senhaGerada) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper emailSender = new MimeMessageHelper(message, true);

        emailSender.setFrom("noreply@matheushenrique.dev.br", applicationConfigService.getApplicationName("settings::app:Name").getValue());
        emailSender.setTo(email.getTo());
        emailSender.setSubject(email.getSubject());

        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"pt-br\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; background-color: #f4f4f4; padding: 20px; }" +
                ".email-container { max-width: 600px; margin: 0 auto; background-color: #fff; border-radius: 8px; padding: 20px; box-shadow: 0 4px 6px rgba(0, 0, 0, .1); }" +
                "h1 { color: #FF5733; }" +
                "p { font-size: 16px; line-height: 1.5; }" +
                ".button { background-color: #FF5733; color: #fff; text-align: center; padding: 12px 20px; border-radius: 5px; text-decoration: none; display: inline-block; }" +
                ".footer { text-align: center; font-size: 12px; color: #888; margin-top: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"email-container\">" +
                "<h1>Nova Senha Gerada</h1>" +
                "<p>Olá, " + email.getFistName() + " " + email.getLastName() + ",</p>" +
                "<p>Sua nova senha foi gerada com sucesso. Utilize a senha abaixo para acessar sua conta:</p>" +
                "<p><strong>Senha: " + senhaGerada + "</strong></p>" +
                "<p>Recomendamos que você altere a senha assim que fizer login para maior segurança.</p>" +
                "<div class=\"footer\"><p>&copy; 2024 SGPA | Todos os direitos reservados.</p></div>" +
                "</div>" +
                "</body>" +
                "</html>";

        emailSender.setText(htmlContent, true);

        mailSender.send(message);
    }
}
