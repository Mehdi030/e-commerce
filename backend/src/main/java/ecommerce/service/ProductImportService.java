package ecommerce.service;

import ecommerce.dto.ProductDTO;
import ecommerce.entity.Product;
import ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductImportService {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public ProductImportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.restTemplate = new RestTemplate();
    }

    public void importProducts() {
        String apiUrl = "https://fakestoreapi.com/products";
        ProductDTO[] products = restTemplate.getForObject(apiUrl, ProductDTO[].class);

        if (products != null) {
            List<Product> productList = Arrays.stream(products)
                    .filter(dto -> !productRepository.existsByName(dto.getTitle())) // 🔥 Vermeidet Duplikate
                    .map(dto -> new Product(
                            dto.getTitle(),
                            dto.getDescription(),
                            (dto.getPrice() != null) ? dto.getPrice() : BigDecimal.ZERO, // 🔥 Sicherheit gegen Nullwerte
                            dto.getImage()
                    ))
                    .toList();

            if (!productList.isEmpty()) {
                productRepository.saveAll(productList);
                System.out.println("✅ " + productList.size() + " neue Produkte importiert!");
            } else {
                System.out.println("⚠️ Keine neuen Produkte zum Importieren gefunden.");
            }
        }
    }
}
