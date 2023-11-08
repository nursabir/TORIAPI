package com.example.ToriApi.User.PrivacyOptions;

import com.example.ToriApi.User.DTO.RegisterUserRequestDto;
import com.example.ToriApi.User.Entityes.User;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
   // @ApiOperation(value = "Зарегистрировать пользователя")
    //@ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 409, message = "We have this login"), @ApiResponse(code = 201, message = "Успешно создан", response = RegisterUserRequestDto.class)} )
    @Operation(
            summary = "Зарегистрировать пользователя",
            description = "Необходимо делать запрос когда пользователь вводит свои параметры для регистрации"
    )
    public ResponseEntity<?> userRegistration(@RequestBody RegisterUserRequestDto dto) {
        return userPrivacyService.createUser(dto);
    }


    @Operation(
            summary = "Вход",
            description = "Вход в приложение по логину и паролю"
    )
    @PostMapping("/entry")
    public ResponseEntity<User> entranceInTory(@RequestParam String login, @RequestParam String password) {
        return userPrivacyService.allowInTori(login, password);
    }

    @Operation(
            summary = "Обновить по json usera",
            description = "В базе поменяются сведения о юзере"
    )
    @PostMapping("/updateData")
    public ResponseEntity<User> updateUserData(@RequestBody User user) {
        return userPrivacyService.updateUserData(user);
    }
}
