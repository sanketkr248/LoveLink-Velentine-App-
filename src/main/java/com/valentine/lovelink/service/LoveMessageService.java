package com.valentine.lovelink.service;

import com.valentine.lovelink.entity.LoveMessage;
import com.valentine.lovelink.repository.LoveMessageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoveMessageService {

    private final LoveMessageRepository repository;

    public LoveMessageService(LoveMessageRepository repository) {
        this.repository = repository;
    }

    public LoveMessage createLoveMessage(String sender, String receiver, String message) {

        LoveMessage love = new LoveMessage();
        love.setSenderName(sender);
        love.setReceiverName(receiver);
        love.setMessage(message);

        // generate unique link code
        String linkCode = UUID.randomUUID().toString().substring(0, 8);
        love.setLinkCode(linkCode);

        return repository.save(love);
    }

    public Optional<LoveMessage> getLoveByLinkCode(String linkCode) {
        return repository.findByLinkCode(linkCode);
    }

}
