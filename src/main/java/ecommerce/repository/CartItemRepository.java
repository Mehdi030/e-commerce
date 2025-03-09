package ecommerce.repository;

import ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    // ✅ Löscht alle Warenkorb-Einträge, die älter als 30 Minuten sind
    void deleteByCreatedAtBefore(LocalDateTime expiryTime);

    // ✅ Gibt alle Einträge eines bestimmten Benutzers zurück (wenn wir User später hinzufügen)
    List<CartItem> findByCreatedAtAfter(LocalDateTime recentTime);
}
