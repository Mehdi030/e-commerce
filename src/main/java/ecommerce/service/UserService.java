package ecommerce.service;

import ecommerce.entity.User;
import ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public void registerUser(User user) {
        System.out.println("🔐 Vor Hashing: " + user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("🔐 Nach Hashing: " + user.getPassword());

        userRepository.save(user);
        sendConfirmationEmail(user);
    }

    private void sendConfirmationEmail(User user) {
        String subject = "Bestätigung deiner Registrierung";
        String message = "Hallo " + user.getUsername() + ",\n\nBitte bestätige deine Registrierung durch Klicken auf den folgenden Link: \n" +
                "http://localhost:8080/auth/confirm?token=" + user.getId();
        emailService.sendEmail(user.getEmail(), subject, message);
    }

    @Transactional
    public Optional<User> confirmUser(UUID userId) {
        return userRepository.findById(userId).map(user -> {
            if (!user.isConfirmed()) {
                user.setConfirmed(true);
                userRepository.save(user);
            }
            return user;
        });
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User findByEmail(String email) {
        System.out.println("🔍 DEBUG: Suche User mit Email = " + email);

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            System.out.println("❌ Kein Benutzer mit dieser Email gefunden!");
            printAllUsers(); // Gibt alle User aus, um zu prüfen, ob es an Groß-/Kleinschreibung liegt
        } else {
            System.out.println("✅ Benutzer gefunden: " + userOpt.get().getEmail());
        }

        return userOpt.orElse(null);
    }


    public void printAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println("📌 Alle gespeicherten Benutzer in der Datenbank:");
        for (User u : users) {
            System.out.println("📌 Benutzer: " + u.getEmail());
        }
    }

    public boolean checkPassword(String rawPassword, String storedHash) {
        System.out.println("🔍 DEBUG: Passwortprüfung gestartet...");
        System.out.println("🔐 Eingegebenes Passwort: " + rawPassword);
        System.out.println("🔑 Gespeichertes Passwort-Hash: " + storedHash);

        boolean matches = passwordEncoder.matches(rawPassword, storedHash);

        System.out.println(matches ? "✅ Passwort korrekt!" : "❌ Passwort falsch!");

        return matches;
    }

}
