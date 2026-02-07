package com.valentine.lovelink.controller;

import com.valentine.lovelink.entity.LoveMessage;
import com.valentine.lovelink.service.LoveMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoveUIController {

    private final LoveMessageService service;

    public LoveUIController(LoveMessageService service) {
        this.service = service;
    }

    // 1️⃣ Open form page
    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    // 2️⃣ Handle form submit
    @PostMapping("/love/create-ui")
    public String createLoveUI(@RequestParam String senderName,
                               @RequestParam String receiverName,
                               @RequestParam String message,
                               Model model) {

        LoveMessage love = service.createLoveMessage(senderName, receiverName, message);
        String shareLink = "http://lovelinkbysanket/love/view/" + love.getLinkCode();

        model.addAttribute("shareLink", shareLink);
        return "success";
    }

    // 3️⃣ View love message
    @GetMapping("/love/view/{code}")
    public String viewLove(@PathVariable String code, Model model) {

        LoveMessage love = service.getLoveByLinkCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid or expired link"));

        model.addAttribute("senderName", love.getSenderName());
        model.addAttribute("receiverName", love.getReceiverName());
        model.addAttribute("message", love.getMessage());

        return "view";
    }
}
