package com.example.sweater.controllers;

import com.example.sweater.domain.Message;
import com.example.sweater.repositoeies.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("/main")
    public String addMessage(@RequestParam String text, @RequestParam String tag){
        if (!text.isEmpty() && !tag.isEmpty()) {
            messageRepository.save(new Message(text, tag));
        }
        return "redirect:/main";
    }

    @PostMapping(path = "/filter")
    public String addMessage(@RequestParam String filter, Model model){
        if (!filter.isEmpty()) {
            model.addAttribute("messages", messageRepository.findAllByTag(filter));
        } else {
            model.addAttribute("messages", messageRepository.findAll());
        }
        return "main";
    }
}