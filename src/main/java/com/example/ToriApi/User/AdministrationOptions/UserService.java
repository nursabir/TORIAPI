package com.example.ToriApi.User.AdministrationOptions;

import com.example.ToriApi.User.User;
import com.example.ToriApi.User.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Bulat Sharapov
 */
@Service
@AllArgsConstructor

public class UserService {
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserAdministrationController.class);


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

    public ResponseEntity<?> sendFriendRequest(Integer id, String login) {
        System.out.println("Отправляем заявку в друзья");
        try {
            Optional<User> userOptional = userRepository.findById(id);
            Optional<User> userOptional2 = userRepository.findByLogin(login);

            if (userOptional.isPresent() && userOptional2.isPresent()) {
                User user = userOptional.get(); // чел, который отправляет заявку

                User user2 = userOptional2.get(); // чел, которому заявку отправили
//                user.getFriends().add(login);// создали заявку как друга
                user2.getFriendsRequests().add(user.getLogin()); // add login as request
                // если так кринжово делаем, то фотка будет отображаться тогда, когда оба друг
                // у друга во friendsList
                return ResponseEntity.ok().body("Заявку создали");
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

    public ResponseEntity<?> acceptFriendRequest(Integer id, String login) {
        System.out.println("Принимаем заявку в друзья");
        try {
            Optional<User> userOptional = userRepository.findById(id);
            Optional<User> userOptional2 = userRepository.findByLogin(login);

            if (userOptional.isPresent() && userOptional2.isPresent()) {
                User userAccepter = userOptional.get(); // чел, который принимает заявку
                User userRequester = userOptional2.get(); // чел, который отправил

                // друг другу логины как кореша
                userAccepter.getFriends().add(userRequester.getLogin());
                userRequester.getFriends().add(userAccepter.getLogin());
                
                //удаляем такой фриенд реквест
                userRepository.removeFromFriendsRequestsById(userAccepter.getId(), userRequester.getLogin());
                // TODO: 24.10.2023  как я пойму у того ли типа я удаляю ?

                
                // если так кринжово делаем, то фотка будет отображаться тогда, когда оба друг
                // у друга во friendsList
                return ResponseEntity.ok().body("Заявку создали");
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

    public ResponseEntity<?> rejectFriendRequest(Integer id, String login) {
        System.out.println("Отклоняем заявку в друзья");
        try {
            Optional<User> userOptional = userRepository.findById(id);
            Optional<User> userOptional2 = userRepository.findByLogin(login);

            if (userOptional.isPresent() && userOptional2.isPresent()) {
                User userAccepter = userOptional.get(); // чел, который отклоняет заявку
                User userRequester = userOptional2.get(); // чел, который отправил

                //удаляем такой фриенд реквест
                userRepository.removeFromFriendsRequestsById(userAccepter.getId(), userRequester.getLogin());
                // если так кринжово делаем, то фотка будет отображаться тогда, когда оба друг
                // у друга во friendsList

                return ResponseEntity.ok().body("Заявку создали");
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
}
