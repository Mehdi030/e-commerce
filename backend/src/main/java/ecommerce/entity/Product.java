package ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public Product() {
        this.price = BigDecimal.ZERO;
    }

    public Product(String name, String description, BigDecimal price, String imageUrl) {
        this.name = name;
        this.description = (description != null) ? description : "";
        this.price = (price != null) ? price : BigDecimal.ZERO;
        this.imageUrl = (imageUrl != null) ? imageUrl : "";
    }

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
        this.description = (description != null) ? description : "";
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = (price != null) ? price : BigDecimal.ZERO;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = (imageUrl != null) ? imageUrl : "";
    }
}
