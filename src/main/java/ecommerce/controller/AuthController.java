package ecommerce.controller;

import ecommerce.entity.User;
import ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
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
    public String showLoginForm(Model model) {
        model.addAttribute("error", false);
        return "login";
    }

    // Verarbeitung des Logins mit Spring Security
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        System.out.println("🛠 Login-Versuch für: " + email);

        if (email == null || email.trim().isEmpty()) {
            System.out.println("❌ E-Mail ist NULL oder LEER!");
            model.addAttribute("error", "Ungültige Anmeldeinformationen!");
            return "login";
        }

        User user = userService.findByEmail(email);
        System.out.println("🔍 Gesuchte E-Mail: " + email);

        if (user == null) {
            System.out.println("❌ Benutzer nicht gefunden in der Datenbank!");
            // Zeige alle gespeicherten User an (prüfe, ob die E-Mail vorhanden ist)
            userService.printAllUsers();
            model.addAttribute("error", "Ungültige Anmeldeinformationen!");
            return "login";
        }
        System.out.println("✅ Benutzer gefunden: " + user.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("✅ Login erfolgreich für: " + email);
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("❌ Login fehlgeschlagen: " + e.getMessage());
            model.addAttribute("error", "Ungültige Anmeldeinformationen!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/auth/login?logout";
    }
}
