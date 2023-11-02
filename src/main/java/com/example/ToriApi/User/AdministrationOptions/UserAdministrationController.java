package com.example.ToriApi.User.AdministrationOptions;

import com.example.ToriApi.User.Entityes.User;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Bulat Sharapov
 */

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
/* в обем в этом контроллере любая административная логика взаимодействия с пользователем  */
public class UserAdministrationController {

    // TODO: 19.10.2023  возможно надо не только айди сохранять, но и айпи телефоона или имей
// TODO: 20.10.2023 Баха сказал, что все хуйня надо переделывать
    private UserService userService;

    @GetMapping("/check")
    public ResponseEntity<String> check(){
        return ResponseEntity.ok().body("Кэмалоф Лен");
    }

    @PostMapping("/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }

    /**
     * @param id    айди человека, который отправляет заявку на добавление в друзья
     * @param login логин человека, которому он отправляет эту заявку
     */
    @PostMapping(value = "{id}/sendFriendRequest/{login}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> sendFriendRequest(@PathVariable Integer id, @PathVariable String login) {
        return userService.sendFriendRequest(id, login);
    }

    /**
     * @param id    айди человека, которому отправили заявку
     * @param login логин человека, который отправил заявку
     */
    @PostMapping("{id}/acceptFriend/{login}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Integer id, @PathVariable String login) {
        return userService.acceptFriendRequest(id, login);
    }

    /**
     * Метод будет отклонять заявку на добавляение в кореша
     *
     * @param id    айди человека, которому отправили заявку
     * @param login логин человека, который отправил заявку
     */
    @PostMapping("{id}/rejectFriend/{login}")
    public ResponseEntity<?> rejectFriendRequest(@PathVariable Integer id, @PathVariable String login) {
        return userService.rejectFriendRequest(id, login);
    }

    /**
     * Метод будет удалять заявку на добавление в кореша
     *
     * @param id    айди человека, который удаляет друга
     * @param login айди друга (бывшего) типа марухи
     */
    @PostMapping("{id}/deleteFriend/{login}")
    public ResponseEntity<?> deleteFriend(@PathVariable Integer id, @PathVariable String login) {
        return userService.deleteFriend(id, login);
    }
}
