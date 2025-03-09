package ecommerce.controller;

import ecommerce.entity.User;
import ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Zeigt das Registrierungsformular an
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Verarbeitung der Registrierung
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        // Überprüfen, ob die E-Mail bereits existiert
        if (userService.emailExists(user.getEmail())) {
            result.rejectValue("email", "error.user", "Diese E-Mail ist bereits registriert.");
            return "register";
        }

        userService.registerUser(user);
        model.addAttribute("message", "Registrierung erfolgreich! Überprüfe deine E-Mails zur Bestätigung.");
        return "login";
    }

    // Bestätigung der Registrierung über den Link in der E-Mail
    @GetMapping("/confirm")
    public String confirmUser(@RequestParam("token") UUID userId, Model model) {
        Optional<User> userOpt = userService.confirmUser(userId);
        if (userOpt.isPresent()) {
            model.addAttribute("message", "Registrierung bestätigt! Du kannst dich jetzt einloggen.");
            return "login";
        } else {
            model.addAttribute("error", "Ungültiger Bestätigungslink oder Benutzer bereits bestätigt!");
            return "error";
        }
    }

    // Zeigt die Login-Seite an
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
