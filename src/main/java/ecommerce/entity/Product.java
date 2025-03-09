package ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String description;
    private BigDecimal price;

    // Standard-Konstruktor (wird von Hibernate benötigt)
    public Product() {}

    // Konstruktor ohne UUID (damit Hibernate sie selbst setzt)
    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        // ID wird automatisch durch @GeneratedValue erzeugt
    }

    // ✅ Getter & Setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
