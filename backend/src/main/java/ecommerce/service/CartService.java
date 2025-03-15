package ecommerce.service;

import ecommerce.entity.Cart;
import ecommerce.entity.CartItem;
import ecommerce.entity.Product;
import ecommerce.repository.CartItemRepository;
import ecommerce.repository.CartRepository;
import ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    // ✅ Erstellt einen neuen Warenkorb und gibt die ID zurück
    public UUID createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cart.getId();
    }

    // ✅ Warenkorb abrufen oder neuen erstellen
    public Cart getCart(UUID cartId) {
        return cartRepository.findById(cartId).orElseGet(() -> {
            Cart newCart = new Cart();
            cartRepository.save(newCart);
            return newCart;
        });
    }

    // ✅ Produkt zum Warenkorb hinzufügen
    public void addItemToCart(UUID cartId, UUID productId, int quantity) {
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Produkt nicht gefunden"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem(cart, product, quantity);
            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    // ✅ Produkt aus Warenkorb entfernen
    public void removeItemFromCart(UUID cartId, UUID productId) {
        Cart cart = getCart(cartId);
        cart.getCartItems().removeIf(cartItem -> {
            boolean match = cartItem.getProduct().getId().equals(productId);
            if (match) cartItemRepository.delete(cartItem);
            return match;
        });

        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    // ✅ Produktmenge aktualisieren
    public void updateItemQuantity(UUID cartId, UUID productId, int newQuantity) {
        Cart cart = getCart(cartId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produkt nicht im Warenkorb"));

        if (newQuantity <= 0) {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        }

        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    // ✅ Gesamten Warenkorb leeren
    public void clearCart(UUID cartId) {
        Cart cart = getCart(cartId);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    // ✅ Aktualisiert den Gesamtpreis
    private void updateTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }
}
