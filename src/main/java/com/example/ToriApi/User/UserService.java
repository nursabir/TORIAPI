package com.example.ToriApi.User;

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

public class UserService {
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * @param user нужен для регистрации юзера
     * @return В этом случае ? (wildcard type) мне нужен т.к я могу вернуть либо сообщение, либо созданного пользователя
     * Знак вопроса <?> указывает, что тип данных неопределен и может быть заменен на любой тип в контексте использования
     */
    public ResponseEntity<?> createUser(User user) {
        System.out.println("Создаем юзера");
        // TODO: 20.10.2023 такой чисто экспериментальный подход для меня. т.к я другому методу все делигирую
        if (getUserByLogin(user.getLogin()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("We have this login in database: ");
        else {
            User createUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
        }
//        try {
//            if (getUserByLogin(user.getLogin()) != null) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).body("We have this login in database: ");
//            }
//            User createUser = userRepository.save(user);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
//        } catch (Exception exception) {
//            logger.error("Internal server error", exception);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }

    }

    /**
     * @param login айди юзера
     * @return Возвращаем юзера по айди.
     * Один из способов применения - при отправке фото друзьям.
     * Мб доработать надо будет
     * ʕ ᵔᴥᵔ ʔ Переделал и ща мы ищем не по айди, а по логину
     */
    public ResponseEntity<User> getUserByLogin(String login) {
        System.out.println("Ищем по логину");
        try {
            User user = userRepository.findByLogin(login)
                    .orElseThrow(() -> new ResourceNotFoundException("We dont have this login in database: " + login));
            return ResponseEntity.ok().body(user);
        } catch (ResourceNotFoundException exception) {
            logger.error("Error while getting user", exception);
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            logger.error("Internal server error", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @GetMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
//        System.out.println("Ищем по айдишнику");
//        try {
//            User user = userRepository.findById(id)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
//            return ResponseEntity.ok().body(user);
//        } catch (ResourceNotFoundException exception) {
//            logger.error("Error while getting user", exception);
//            return ResponseEntity.notFound().build();
//        } catch (Exception exception) {
//            logger.error("Internal server error", exception);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


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
