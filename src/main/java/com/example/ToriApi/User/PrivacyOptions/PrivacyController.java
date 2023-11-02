package com.example.ToriApi.User.PrivacyOptions;

import com.example.ToriApi.User.DTO.RegisterUserRequestDto;
import com.example.ToriApi.User.Entityes.User;
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

    /**
     * @param dto нужен для регистрации юзера
     * @return В этом случае ? (wildcard type) мне нужен т.к я могу вернуть либо сообщение, либо созданного пользователя
     * Знак вопроса <?> указывает, что тип данных неопределен и может быть заменен на любой тип в контексте использования
     */
    @PostMapping("/registerUser")
    public ResponseEntity<?> userRegistration(@RequestBody RegisterUserRequestDto dto) {
        return userPrivacyService.createUser(dto);
    }

    @PostMapping("/entry")
    public ResponseEntity<User> entranceInTory(@RequestParam String login, @RequestParam String password) {
        return userPrivacyService.allowInTori(login, password);
    }

    @PostMapping("/updateData")
    public ResponseEntity<User> updateUserData(@RequestBody User user) {
        return userPrivacyService.updateUserData(user);
    }
}
