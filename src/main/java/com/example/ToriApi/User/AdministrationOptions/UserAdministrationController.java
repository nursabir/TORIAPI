package com.example.ToriApi.User.AdministrationOptions;

import com.example.ToriApi.User.Entityes.User;
import io.swagger.v3.oas.annotations.Operation;
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



    @Operation(
            summary = "Получить json объект user",
            description = "По указанному логину возвращает юзера"
    )
    @PostMapping("/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }

    /**
     * @param id    айди человека, который отправляет заявку на добавление в друзья
     * @param login логин человека, которому он отправляет эту заявку
     */
    @Operation(
            summary = "Запрос на дружбу",
            description = "В базе отобразится что у user с указанном id будет заявка у юзера с указанным логином  "
    )

    @PostMapping(value = "{id}/sendFriendRequest/{login}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> sendFriendRequest(@PathVariable Integer id, @PathVariable String login) {
        return userService.sendFriendRequest(id, login);
    }

    /**
     * @param id    айди человека, которому отправили заявку
     * @param login логин человека, который отправил заявку
     */
    @Operation(
            summary = "Принять заявку",
            description = "В базу будет внесено что пользователь c id принял заявку в друзья пользователя с login"
    )
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
    @Operation(
            summary = "Отклонить заявку в друзья",
            description = "Отклонение у пользователя с id заявки в друзья от пользователя с login"
    )
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
    @Operation(
            summary = "Удалить друга",
            description = "Удалит у пользователя с id друга с login"
    )
    @PostMapping("{id}/deleteFriend/{login}")
    public ResponseEntity<?> deleteFriend(@PathVariable Integer id, @PathVariable String login) {
        return userService.deleteFriend(id, login);
    }
}
