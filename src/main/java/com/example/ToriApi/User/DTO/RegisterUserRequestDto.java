package com.example.ToriApi.User.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author Bulat Sharapov
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Сущнсоть для регистрации")
public class RegisterUserRequestDto {
//    @Max(value = 8, message = "логин не может быть больше 8")
//    @Min(value = 4, message = "логин не может быть меньше 4")
    String login;
    String password;
    String email;
}
