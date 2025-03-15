package ecommerce;

import ecommerce.service.ProductImportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 🔥 Aktiviert den Scheduler für automatische Tasks
@SpringBootApplication(scanBasePackages = "ecommerce")
public class ShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ProductImportService productImportService) {
		return args -> {
			System.out.println("🔄 Starte den Produktimport...");
			productImportService.importProducts();
			System.out.println("✅ Produktimport abgeschlossen!");
		};
	}
}
