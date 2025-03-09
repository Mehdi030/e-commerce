package ecommerce.controller;

import ecommerce.entity.Product;
import ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products"; // Verweist auf products.html
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable UUID id, Model model) {
        model.addAttribute("product", productService.getProductById(id).orElse(null));
        return "product-detail"; // Verweist auf product-detail.html
    }

    // 🔹 Neues Produkt hinzufügen (Formular anzeigen)
    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form"; // Zeigt ein Formular für neue Produkte
    }

    // 🔹 Neues Produkt speichern
    @PostMapping("/save")
    public String createProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products"; // Leitet zurück zur Produktliste
    }

    // 🔹 Produkt löschen
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    // 🔹 Produkte nach Preis sortieren (optional)
    @GetMapping("/sorted")
    public String getProductsSortedByPrice(Model model) {
        List<Product> products = productService.getAllProductsSortedByPrice();
        model.addAttribute("products", products);
        return "products";
    }
}
