package ecommerce.service;

import ecommerce.entity.User;
import ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 🔥 PasswordEncoder wird jetzt richtig erkannt
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 🔥 Verschlüsselung mit BCrypt
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
    public User confirmUser(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && !user.isConfirmed()) {
            user.setConfirmed(true);
            userRepository.save(user);
        }
        return user;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
