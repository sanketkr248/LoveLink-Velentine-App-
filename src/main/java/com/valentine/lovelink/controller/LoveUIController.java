package com.valentine.lovelink.controller;

import com.valentine.lovelink.entity.LoveMessage;
import com.valentine.lovelink.service.LoveMessageService;
import jakarta.servlet.http.HttpServletRequest;
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
                               HttpServletRequest request,
                               Model model) {

        LoveMessage love = service.createLoveMessage(senderName, receiverName, message);

        // Build base URL dynamically
        String baseUrl = request.getScheme() + "://" + request.getServerName()
                + ((request.getServerPort() == 80 || request.getServerPort() == 443)
                ? ""
                : ":" + request.getServerPort());

        // Final shareable link
        String shareLink = baseUrl + "/love/view/" + love.getLinkCode();

        // Send to UI
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
