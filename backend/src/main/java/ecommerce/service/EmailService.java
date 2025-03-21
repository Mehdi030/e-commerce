package ecommerce.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // ✅ Methode für normale E-Mails (z.B. Bestätigungsmails)
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true für HTML-Unterstützung
            helper.setFrom("noreply@ecommerce.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Fehler beim Senden der E-Mail", e);
        }
    }

    // ✅ Methode für Bestellbestätigung mit PDF-Rechnung
    public void sendOrderConfirmation(String to, String subject, String text, File pdfInvoice) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setFrom("noreply@ecommerce.com");

        if (pdfInvoice != null) {
            helper.addAttachment("Rechnung.pdf", pdfInvoice);
        }

        mailSender.send(message);
    }
}
