package ecommerce;

import ecommerce.service.EmailTest;
import jakarta.mail.MessagingException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmailTestRunner implements CommandLineRunner {

    private final EmailTest emailTest;

    public EmailTestRunner(EmailTest emailTest) {
        this.emailTest = emailTest;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            emailTest.sendTestEmail();
        } catch (MessagingException e) {
            System.out.println("❌ Fehler beim Senden der Test-Mail: " + e.getMessage());
        }
    }
}
