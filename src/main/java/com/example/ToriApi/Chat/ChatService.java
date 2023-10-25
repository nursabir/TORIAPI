package com.example.ToriApi.Chat;

import com.example.ToriApi.User.AdministrationOptions.UserAdministrationController;
import com.example.ToriApi.User.User;
import com.example.ToriApi.User.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Bulat Sharapov
 */

@Service
@AllArgsConstructor
public class ChatService {
    private ChatRepository chatRepository;
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserAdministrationController.class);

    //не дает вернуть ок, если поставить тип CHAT
    public ResponseEntity<?> sendMessage(String loginFrom, String loginTo, String message) {
        System.out.println("Отправляем сообщение");
        try {
            Chat chat = new Chat();
            Optional<User> userFrom = userRepository.findByLogin(loginFrom); // человек, который отправляет
            Optional<User> userTo = userRepository.findByLogin(loginTo); // человек, которому отправляют

            if (userFrom.isPresent() && userTo.isPresent()) {
                chat.setLoginFrom(userFrom.get().getLogin());  // Заполнение полей сообщения
                chat.setLoginTo(userTo.get().getLogin());
                chat.setMessage(message);
                chatRepository.save(chat); // Сохранение сообщения в базе данных
                return ResponseEntity.ok().body("Сообщение отправлено");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResourceNotFoundException exception) {
            logger.error("Error while getting user", exception);
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            logger.error("Internal server error", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ТУТ АНАЛОГИЧНАЯ ИСТОРИЯ
    public ResponseEntity<?> getMessages(String loginTo){
        System.out.println("Получаем сообщение сообщение");
        try {
            Optional<Chat> chatOptional = chatRepository.findChatByLoginTo(loginTo); // Поиск сообщения по логину

            if (chatOptional.isPresent()) {
                Chat chat = chatOptional.get();
                return ResponseEntity.ok(chat.getMessage()); // Возврат найденного сообщения
            } else {
                return ResponseEntity.notFound().build(); // Сообщение не найдено
            }

        } catch (ResourceNotFoundException exception) {
            logger.error("Error while getting chat", exception);
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            logger.error("Internal server error", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
