package com.example.ToriApi.User.PrivacyOptions;

import com.example.ToriApi.User.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Bulat Sharapov
 */

@RestController
@RequestMapping("/api/v1/privacy")
@AllArgsConstructor
public class PrivacyController {

    private UserPrivacyService userPrivacyService;

    @PostMapping("/register")
    public ResponseEntity<?> userRegistration(@RequestBody User user) {
        return userPrivacyService.createUser(user);
    }

    @PostMapping("/entry")
    public ResponseEntity<User> entranceInTory(@RequestParam String login, @RequestParam String password) {
        return userPrivacyService.allowIn(login, password);
    }

    @PostMapping("/updateData")
    public ResponseEntity<User> updateUserData(@RequestBody User user) {
        return userPrivacyService.updateUserData(user);
    }
}
