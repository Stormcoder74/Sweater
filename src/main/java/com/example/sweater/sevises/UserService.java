package com.example.sweater.sevises;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repositoeies.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MailService mailService;

    public UserService(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String addUser(User user) {
        if (user.getUsername().isEmpty()
                || user.getPassword().isEmpty()
                || user.getEmail().isEmpty()) {
            return "Fill in all form fields";
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "This user already exist";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        String message = String.format(
                        "Hello, %s!\n" +
                        "Welcome no Sweater! Visit this link to complete yor registration!\n" +
                        "<a href = \"http://localhost:8080/activate/%s\">Confirm link</a>",
                user.getUsername(),
                user.getActivationCode()
        );

        try {
            mailService.send(user.getEmail(), "Activation account", message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean activateAccount(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user != null) {
            user.setActivationCode(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }
}
