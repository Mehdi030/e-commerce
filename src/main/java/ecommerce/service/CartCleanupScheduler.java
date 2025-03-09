package ecommerce.service;

import ecommerce.repository.CartItemRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CartCleanupScheduler {

    private final CartItemRepository cartItemRepository;

    public CartCleanupScheduler(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    // ✅ Läuft alle 5 Minuten & löscht Warenkorb-Items, die älter als 30 Minuten sind
    @Scheduled(fixedRate = 900000) // 15min = 900000ms
    public void clearExpiredCartItems() {
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(30);
        cartItemRepository.deleteByCreatedAtBefore(expiryTime);
        System.out.println("🗑️ Alte Warenkorb-Items gelöscht");
    }
}
