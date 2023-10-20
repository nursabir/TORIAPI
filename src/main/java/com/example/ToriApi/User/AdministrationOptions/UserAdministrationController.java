package com.example.ToriApi.User.AdministrationOptions;

import com.example.ToriApi.User.User;
import lombok.AllArgsConstructor;
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

    //    // TODO: 19.10.2023  возможно надо не только айди сохранять, но и айпи телефоона или имей
//    // TODO: 20.10.2023 Баха сказал, что все хуйня надо переделывать
    private UserService userService;
    @PostMapping("/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }
}
