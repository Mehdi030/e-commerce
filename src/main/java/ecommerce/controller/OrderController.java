package ecommerce.controller;

import ecommerce.service.OrderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/complete")
    public String completeOrder(@RequestParam Long orderId,
                                @RequestParam String customerEmail,
                                @RequestParam String customerName,
                                @RequestParam double totalAmount) {

        try {
            orderService.completeOrder(orderId, customerEmail, customerName, totalAmount);
            return "Bestellung abgeschlossen und Bestätigungsmail gesendet.";
        } catch (FileNotFoundException | MessagingException e) {
            return "Fehler: " + e.getMessage();
        }
    }
}

