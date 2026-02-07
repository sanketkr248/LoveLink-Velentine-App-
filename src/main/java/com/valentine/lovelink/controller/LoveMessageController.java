package com.valentine.lovelink.controller;

import com.valentine.lovelink.entity.LoveMessage;
import com.valentine.lovelink.service.LoveMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/love")
public class LoveMessageController {

    private final LoveMessageService service;

    public LoveMessageController(LoveMessageService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Map<String, String> createLove(@RequestBody Map<String, String> request) {

        LoveMessage love = service.createLoveMessage(
                request.get("senderName"),
                request.get("receiverName"),
                request.get("message")
        );

        Map<String, String> response = new HashMap<>();
        response.put("linkCode", love.getLinkCode());
        response.put("shareLink", "https://lovelinkbysanket/love/" + love.getLinkCode());

        return response;
    }
    @GetMapping("/{code}")
    public Map<String, String> getLoveMessage(@PathVariable("code") String code) {

        return service.getLoveByLinkCode(code)
                .map(love -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("senderName", love.getSenderName());
                    response.put("receiverName", love.getReceiverName());
                    response.put("message", love.getMessage());
                    response.put("createdAt", love.getCreatedAt().toString());
                    return response;
                })
                .orElseGet(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Invalid or expired love link 💔");
                    return error;
                });
    }

}
