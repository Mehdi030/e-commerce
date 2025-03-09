package ecommerce.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTest {

    private final JavaMailSender mailSender;

    public void sendTestEmail() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo("test@example.com");  // Empfänger (ersetze mit deiner Test-Adresse)
        helper.setSubject("Test-Mail über Mailtrap");
        helper.setText("Hallo! Dies ist eine Test-Mail über Mailtrap.io. Wenn du diese Mail siehst, funktioniert alles richtig! 🎉");
        helper.setFrom("noreply@ecommerce.com");

        mailSender.send(message);
        System.out.println("✅ Test-Mail wurde erfolgreich gesendet!");
    }
}
