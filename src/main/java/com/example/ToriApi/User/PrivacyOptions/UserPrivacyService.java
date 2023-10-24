package com.example.ToriApi.User.PrivacyOptions;

import com.example.ToriApi.User.User;
import com.example.ToriApi.User.AdministrationOptions.UserAdministrationController;
import com.example.ToriApi.User.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
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
        try {
            if (userRepository.findById(user.getId()).isPresent())
                return ResponseEntity.status(HttpStatus.CONFLICT).body("We have this id in database: ");
            else {
                User createUser = userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
            }
        } catch (Exception e) {
            logger.error("Smth goes wrong ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    // TODO: 23.10.2023  пока не понимаю что мы тут менять будем.
    // Пока все поменяем кроме внешнего ключа (логин)
    public ResponseEntity<User> updateUserData(User user) {
        System.out.println("Обновляем данные о пользователе");
        try {
            Optional<User> currentUser = userRepository.findById(user.getId());

            if (currentUser.isPresent()) {
                User existingUser = currentUser.get();
                existingUser.setLogin(user.getLogin());

                userRepository.save(existingUser);

                return ResponseEntity.ok(existingUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Smth goes wrong ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
