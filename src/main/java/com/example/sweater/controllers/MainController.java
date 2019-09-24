package com.example.sweater.controllers;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repositoeies.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class MainController {
    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model,
                       @RequestParam(required = false, defaultValue = "") String filter) {

        Iterable<Message> messages = !filter.isEmpty()
                ? messageRepository.findAllByTag(filter)
                : messageRepository.findAll();

        model.addAttribute("messages",messages);
        model.addAttribute("filter",filter);

        return"main";
}

    @PostMapping("/main")
    public String addMessage(
            @RequestParam String text,
            @RequestParam String tag,
            @AuthenticationPrincipal User user,
            @RequestParam("file") MultipartFile file) {

        Message message = null;
        if (!text.isEmpty() && !tag.isEmpty()) {
            message = new Message(text, tag, user);
        }

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty() && message != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                if (!uploadDir.mkdir()) {
                    System.err.println("Место в файловой системе не доступно для записи!");
                }
            }

            String resultFilename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + "\\" + resultFilename));
                message.setFilename(resultFilename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            messageRepository.save(message);
        }
        return "redirect:/main";
    }
}