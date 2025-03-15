package ecommerce.repository;

import ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCreatedAtBefore(LocalDateTime time);
    void deleteByCreatedAtBefore(LocalDateTime time);
}
