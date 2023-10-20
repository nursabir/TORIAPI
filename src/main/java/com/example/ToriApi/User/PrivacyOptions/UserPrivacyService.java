package com.example.ToriApi.User.PrivacyOptions;

import com.example.ToriApi.User.User;
import com.example.ToriApi.User.AdministrationOptions.UserAdministrationController;
import com.example.ToriApi.User.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Bulat Sharapov
 */
@Service
@AllArgsConstructor
public class UserPrivacyService {

    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserAdministrationController.class);

    /**
     * @param user нужен для регистрации юзера
     * @return В этом случае ? (wildcard type) мне нужен т.к я могу вернуть либо сообщение, либо созданного пользователя
     * Знак вопроса <?> указывает, что тип данных неопределен и может быть заменен на любой тип в контексте использования
     */
    public ResponseEntity<?> createUser(User user) {
        System.out.println("Создаем юзера");
        // TODO: 20.10.2023 такой чисто экспериментальный подход для меня. т.к я другому методу все делигирую
        if (userRepository.findByLogin(user.getLogin()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("We have this login in database: ");
        else {
            User createUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
        }
    }

    public ResponseEntity<User> allowIn(String login, String enteredPassword) {
        System.out.println("Проверяем соответствие пароля");
        try {
            User allowInUser = userRepository.findByLogin(login)
                    .orElseThrow(() -> new ResourceNotFoundException("We dont have this login in database: " + login));
            if (enteredPassword.equals(allowInUser.getPassword())) {
                // Пароли совпадают, возвращаем успешный статус со сущностью пользователя
                return ResponseEntity.ok(allowInUser);
            } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            logger.error("Smth goes wrong ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
