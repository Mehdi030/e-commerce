package ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 🔥 Aktiviert den Scheduler für automatische Tasks
@SpringBootApplication(scanBasePackages = "ecommerce")
public class ShoppingCartApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}
}


