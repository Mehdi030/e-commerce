package ecommerce.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final EmailService emailService;
    private final PdfInvoiceGenerator pdfInvoiceGenerator;

    public void completeOrder(Long orderId, String customerEmail, String customerName, double totalAmount)
            throws FileNotFoundException, MessagingException {


        // PDF-Rechnung generieren
        File invoice = pdfInvoiceGenerator.generateInvoice(orderId, customerName, totalAmount);

        // Bestätigungsmail senden
        emailService.sendOrderConfirmation(
                customerEmail,
                "Bestätigung Ihrer Bestellung #" + orderId,
                "Vielen Dank für Ihre Bestellung!",
                invoice
        );
    }
}
