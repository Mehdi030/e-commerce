package ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private BigDecimal price;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Setzt das Datum automatisch bei Erstellung
    }

    public CartItem() {
        this.createdAt = LocalDateTime.now(); // Falls das Objekt manuell instanziiert wird
    }

    // ✅ Neuer Konstruktor für CartItem
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        this.createdAt = LocalDateTime.now();
    }

}