package com.example.ToriApi.Chat;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Bulat Sharapov
 */
public interface ChatRepository extends JpaRepository <Chat, Integer> {

}
