package ecommerce.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

@Service
public class PdfInvoiceGenerator {

    public File generateInvoice(Long orderId, String customerName, double totalAmount) throws FileNotFoundException {
        String filePath = "invoice_" + orderId + ".pdf";
        File file = new File(filePath);

        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Rechnung"));
        document.add(new Paragraph("Bestellnummer: " + orderId));
        document.add(new Paragraph("Kunde: " + customerName));
        document.add(new Paragraph("Gesamtbetrag: " + totalAmount + " €"));

        document.close();
        return file;
    }
}
