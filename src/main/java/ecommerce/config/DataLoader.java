package ecommerce.config;

import ecommerce.entity.Product;
import ecommerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            List<Product> products = Arrays.asList(
                    new Product("Laptop", "Leistungsstarker Gaming-Laptop", new BigDecimal("1499.99")),
                    new Product("Smartphone", "High-End Smartphone mit OLED-Display", new BigDecimal("899.99"))
            );
            productRepository.saveAll(products);
            System.out.println("✅ Test-Produkte wurden hinzugefügt!");
        } else {
            System.out.println("🔄 Produkte bereits vorhanden. Kein erneutes Einfügen.");
        }
    }
}
