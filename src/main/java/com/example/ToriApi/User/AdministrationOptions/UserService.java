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
import java.util.List;
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
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("We dont have this login in database: " + login));
        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<?> sendFriendRequest(Integer id, String login) {
        System.out.println("Отправляем заявку в друзья");
        Optional<User> userOptional = userRepository.findById(id);
        Optional<User> userOptional2 = userRepository.findByLogin(login);

        if (userOptional.isEmpty() || userOptional2.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User userSender = userOptional.get(); // Человек, который отправляет заявку
        User userRequester = userOptional2.get(); // Человек, которому заявку отправили

        // Проверяем, чтобы не было дублирующихся запросов
        if (!userRequester.getFriendsRequests().contains(userSender.getLogin())) {
            userRequester.getFriendsRequests().add(userSender.getLogin()); // Добавляем отправителя в список запросов у получателя

            userRepository.save(userRequester);

            return ResponseEntity.ok().body("Заявку создали");
        } else {
            return ResponseEntity.ok().body("Заявка уже существует");
        }
    }

    public ResponseEntity<?> acceptFriendRequest(Integer id, String login) {
        System.out.println("start");
        System.out.println("Принимаем заявку в друзья");
        Optional<User> userOptional = userRepository.findById(id);
        Optional<User> userOptional2 = userRepository.findByLogin(login);

        if (userOptional.isEmpty() || userOptional2.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (!userOptional.get().getFriendsRequests().contains(login))
            return ResponseEntity.notFound().build();

        User userAccepter = userOptional.get(); // чел, который принимает заявку
        User userRequester = userOptional2.get(); // чел, который отправил

        if (userAccepter.getFriends().contains(login)) {
            //удаляем такой фриенд реквест

            userRepository.updateFriendsRequests(userAccepter.getId(), Collections.singletonList(userRequester.getLogin()));
            return ResponseEntity.badRequest().body("Такой друг уже есть.");
        }

        // друг другу логины как кореша
        userAccepter.getFriends().add(userRequester.getLogin());
        userRequester.getFriends().add(userAccepter.getLogin());

        userAccepter.getFriendsRequests().remove(userRequester.getLogin());
        userRepository.save(userAccepter);

        System.out.println();
        return ResponseEntity.ok().body("Добавили вашего друга !");

    }


    public ResponseEntity<?> rejectFriendRequest(Integer id, String login) {
        System.out.println("Отклоняем заявку в друзья");
        Optional<User> userOptional = userRepository.findById(id);
        Optional<User> userOptional2 = userRepository.findByLogin(login);

        if (userOptional.isEmpty() || userOptional2.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (!userOptional.get().getFriendsRequests().contains(login))
            return ResponseEntity.notFound().build();

        User userWhoRejectFriend = userOptional.get(); // чел, который отклоняет
        User userWhoRejected = userOptional2.get(); // чел, которого отклоняют

        //удаляем такой фриенд реквест
        userWhoRejectFriend.getFriendsRequests().remove(userWhoRejected.getLogin());

//        List<String> friendsRequests = userWhoRejectFriend.getFriendsRequests();
//        friendsRequests.remove(userWhoRejected.getLogin());
        /**
         * мы типа обновляем список без этого логина
         */
        userRepository.save(userWhoRejectFriend);
//        userRepository.updateFriendsRequests(userAccepter.getId(), friendsRequests);
        // если так кринжово делаем, то фотка будет отображаться тогда, когда оба друг
        // у друга во friendsList

        return ResponseEntity.ok().body("Друга Отклонили");
    }

    public ResponseEntity<?> deleteFriend(Integer id, String login) {
        System.out.println("Удаляем из друзей");
        Optional<User> userDeleter = userRepository.findById(id); // чел который удаляет
        Optional<User> userMarukha = userRepository.findByLogin(login);// обиженка маруха

        if (userDeleter.isEmpty() || userMarukha.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User userWhoDeleteFriend = userDeleter.get();
        User userWhoDeleted = userMarukha.get(); // хоть где-то маруха полезен

            if (userWhoDeleteFriend.getLogin().equals(userWhoDeleted.getLogin())){
                return ResponseEntity.badRequest().body("Это вообще то ты сам, другалечек");
            }

        userWhoDeleteFriend.getFriends().remove(userWhoDeleted.getLogin());
        userWhoDeleted.getFriends().remove(userWhoDeleteFriend.getLogin());

        userRepository.save(userWhoDeleteFriend);
        return ResponseEntity.ok().body("Корешок (Маруха) удален");
    }
}
