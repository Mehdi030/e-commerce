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
import java.util.Optional;
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

    public Cart getCart(UUID cartId) {
        return cartRepository.findById(cartId).orElseGet(() -> {
            Cart newCart = new Cart();
            cartRepository.save(newCart);
            return newCart;
        });
    }

    public void addItemToCart(UUID cartId, UUID productId, int quantity) {
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produkt nicht gefunden"));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem newItem = new CartItem(cart, product, quantity);
            cart.getCartItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    public void removeItemFromCart(UUID cartId, UUID productId) {
        Cart cart = getCart(cartId);
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    public void updateItemQuantity(UUID cartId, UUID productId, int quantity) {
        Cart cart = getCart(cartId);
        cart.getCartItems().forEach(item -> {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
            }
        });
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        Cart cart = getCart(cartId);
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    private void updateTotalPrice(Cart cart) {
        BigDecimal total = cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
    }
}
