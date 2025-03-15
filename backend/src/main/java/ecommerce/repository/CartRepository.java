package ecommerce.repository;

import ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    List<Cart> findByCreatedAtBefore(LocalDateTime time); // Findet alle älteren Warenkörbe
    void deleteByCreatedAtBefore(LocalDateTime time); // Löscht alle älteren Warenkörbe
}

