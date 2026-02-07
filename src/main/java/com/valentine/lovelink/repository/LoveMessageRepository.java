package com.valentine.lovelink.repository;

import com.valentine.lovelink.entity.LoveMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoveMessageRepository extends JpaRepository<LoveMessage, Long> {
    Optional<LoveMessage> findByLinkCode(String linkCode);
}
