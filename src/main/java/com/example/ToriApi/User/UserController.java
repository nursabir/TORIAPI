package com.example.ToriApi.User;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Bulat Sharapov
 */
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    //    // TODO: 19.10.2023  возможно надо не только айди сохранять, но и айпи телефоона или имей
//    // TODO: 20.10.2023 Баха сказал, что все хуйня надо переделывать
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> userRegistration(@RequestBody User user) {
        return userService.createUser(user);
    }
@PostMapping("/entry")
    public ResponseEntity<User> entranceInTory(@RequestParam String login, @RequestParam  String password){
    return userService.allowIn(login, password);
    }

    @PostMapping("/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }
}
