package com.example.sweater.sevises;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repositoeies.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       MailService mailService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        sendEmail(user);

        return "";
    }

    private void sendEmail(User user) {
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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }

    public User getByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public void saveProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail))
                || (userEmail != null && !userEmail.isEmpty());
        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
                //todo ошибка при смене почты
                sendEmail(user);
            }
        }

        if (StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        userRepository.save(user);
    }
}
