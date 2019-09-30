package com.example.sweater.controllers;

import com.example.sweater.domain.User;
import com.example.sweater.sevises.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        String addResult = userService.addUser(user);
        if (!addResult.isEmpty()) {
            model.addAttribute("message", addResult);
            return "registration";
        }
        //todo добавить ссылку на почтовый сервис https://habr.com/ru/post/245595/
        model.addAttribute("message", "Registration is successful.<br>" +
                "We sent email no your address.<br>" +
                "Please visit your inbox and follow the<br>" +
                "link to complete your registration");
        return "login";
    }

    @GetMapping("/activate/{code}")
    public String activation(@PathVariable String code, Model model) {
        if (userService.activateAccount(code)) {
            model.addAttribute("message", "Account was activated successfully.");
            return "login";
        } else {
            //todo добавить вывод ошибки (на стартовую страницу?)
            model.addAttribute("message", "Error of activation.");
            return "/";
        }
    }
}
