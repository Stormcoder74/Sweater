package com.example.sweater.controllers;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repositoeies.MessageRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model,
                       @RequestParam(required = false, defaultValue = "") String filter){

        if (!filter.isEmpty()) {
            model.addAttribute("messages", messageRepository.findAllByTag(filter));
        } else {
            model.addAttribute("messages", messageRepository.findAll());
        }
        model.addAttribute("filter", filter);
        
        return "main";
    }

    @PostMapping("/main")
    public String addMessage(
            @RequestParam String text,
            @RequestParam String tag,
            @AuthenticationPrincipal User user){

        if (!text.isEmpty() && !tag.isEmpty()) {
            messageRepository.save(new Message(text, tag, user));
        }
        return "redirect:/main";
    }
}