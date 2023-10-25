package com.example.ToriApi.Chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Bulat Sharapov
 */
public interface ChatRepository extends JpaRepository <Chat, Integer> {
    Optional<Chat> findChatByLoginTo(String login);
}
