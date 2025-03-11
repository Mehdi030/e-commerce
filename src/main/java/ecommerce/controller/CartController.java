package ecommerce.controller;

import ecommerce.entity.Cart;
import ecommerce.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String showCart(@RequestParam UUID cartId, Model model) {
        Cart cart = cartService.getCart(cartId);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add/{cartId}/{productId}")
    public String addToCart(@PathVariable UUID cartId, @PathVariable UUID productId, @RequestParam int quantity) {
        cartService.addItemToCart(cartId, productId, quantity);
        return "redirect:/cart?cartId=" + cartId;
    }

    @PostMapping("/remove/{cartId}/{productId}")
    public String removeFromCart(@PathVariable UUID cartId, @PathVariable UUID productId) {
        cartService.removeItemFromCart(cartId, productId);
        return "redirect:/cart?cartId=" + cartId;
    }

    @PostMapping("/update/{cartId}/{productId}")
    public String updateCartItem(@PathVariable UUID cartId, @PathVariable UUID productId, @RequestParam int quantity) {
        cartService.updateItemQuantity(cartId, productId, quantity);
        return "redirect:/cart?cartId=" + cartId;
    }

    @PostMapping("/clear/{cartId}")
    public String clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return "redirect:/cart?cartId=" + cartId;
    }
}
