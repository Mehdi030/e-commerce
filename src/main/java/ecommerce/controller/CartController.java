package ecommerce.controller;

import ecommerce.entity.Cart;
import ecommerce.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.UUID;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cartId") // 🔥 Speichert `cartId` in der Session
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute("cartId")
    public UUID initializeCart() {
        return cartService.createCart();
    }

    @GetMapping
    public String showCart(@ModelAttribute("cartId") UUID cartId, Model model) {
        Cart cart = cartService.getCart(cartId);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@ModelAttribute("cartId") UUID cartId, @PathVariable UUID productId) {
        cartService.addItemToCart(cartId, productId, 1); // Standardmenge 1
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@ModelAttribute("cartId") UUID cartId, @PathVariable UUID productId) {
        cartService.removeItemFromCart(cartId, productId);
        return "redirect:/cart";
    }

    @PostMapping("/update/{productId}")
    public String updateCartItem(@ModelAttribute("cartId") UUID cartId, @PathVariable UUID productId, @RequestParam int quantity) {
        cartService.updateItemQuantity(cartId, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(@ModelAttribute("cartId") UUID cartId, SessionStatus sessionStatus) {
        cartService.clearCart(cartId);
        sessionStatus.setComplete(); // 🔥 Beendet die Session und löscht `cartId`
        return "redirect:/cart";
    }
}
