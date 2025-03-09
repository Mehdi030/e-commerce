package ecommerce.service;

import ecommerce.entity.Cart;
import ecommerce.entity.CartItem;
import ecommerce.entity.Product;
import ecommerce.repository.CartItemRepository;
import ecommerce.repository.CartRepository;
import ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    // ✅ Warenkorb abrufen
    public Cart getCart(UUID cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Warenkorb nicht gefunden"));
    }

    // ✅ Produkt in Warenkorb legen oder Menge erhöhen
    public Cart addItemToCart(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Warenkorb nicht gefunden"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Produkt nicht gefunden"));

        // Prüfen, ob Produkt bereits im Warenkorb ist
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        updateTotalPrice(cart);
        cartItemRepository.save(cartItem);
        return cartRepository.save(cart);
    }

    // ✅ Produkt aus Warenkorb entfernen
    public Cart removeItemFromCart(UUID cartId, UUID productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Warenkorb nicht gefunden"));

        cart.getCartItems().removeIf(cartItem -> {
            if (cartItem.getProduct().getId().equals(productId)) {
                cartItemRepository.delete(cartItem);
                return true;
            }
            return false;
        });

        updateTotalPrice(cart);
        return cartRepository.save(cart);
    }

    // ✅ Produktmenge im Warenkorb aktualisieren
    public Cart updateItemQuantity(UUID cartId, UUID productId, int newQuantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Warenkorb nicht gefunden"));
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produkt nicht im Warenkorb"));

        if (newQuantity <= 0) {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            cartItemRepository.save(cartItem);
        }

        updateTotalPrice(cart);
        return cartRepository.save(cart);
    }

    // ✅ Gesamten Warenkorb leeren
    public void clearCart(UUID cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Warenkorb nicht gefunden"));
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    // ✅ Automatische Löschung alter Warenkörbe (nach 30 Minuten)
    public void removeOldCarts() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(30);
        List<Cart> oldCarts = cartRepository.findByCreatedAtBefore(expirationTime);
        cartRepository.deleteAll(oldCarts);
    }

    // ✅ Berechnung des Gesamtpreises
    private void updateTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }
}

