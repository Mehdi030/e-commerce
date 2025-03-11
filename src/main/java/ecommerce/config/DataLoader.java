package ecommerce.config;

import ecommerce.entity.Product;
import ecommerce.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataLoader {

    private final ProductRepository productRepository;

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void loadSampleData() {
        if (productRepository.count() == 0) {
            List<Product> products = List.of(
                    new Product("Laptop", "Leistungsstarker Gaming-Laptop", new BigDecimal("1499.99"), "https://example.com/laptop.jpg"),
                    new Product("Smartphone", "High-End Smartphone mit OLED-Display", new BigDecimal("899.99"), "https://example.com/smartphone.jpg"),
                    new Product("Tablet", "Mid-End Tablet mit 4K-Display", new BigDecimal("699.99"), "https://example.com/tablet.jpg")
            );

            productRepository.saveAll(products);
            System.out.println("✅ Testprodukte in die Datenbank geladen!");
        } else {
            System.out.println("⚠️ Produkte bereits vorhanden. Kein erneutes Einfügen.");
        }
    }
}
