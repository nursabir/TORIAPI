package com.example.ToriApi.User.PrivacyOptions;

import com.example.ToriApi.User.DTO.RegisterUserRequestDto;
import com.example.ToriApi.User.Entityes.User;
import com.example.ToriApi.User.AdministrationOptions.UserAdministrationController;
import com.example.ToriApi.User.UserRepository;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Bulat Sharapov
 */
@Service
@AllArgsConstructor
@Getter
@Setter
@ComponentScan("ToriSecurity")
public class UserPrivacyService {

    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserAdministrationController.class);

    //    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param dto нужен для регистрации юзера
     * @return В этом случае ? (wildcard type) мне нужен т.к я могу вернуть либо сообщение, либо созданного пользователя
     * Знак вопроса <?> указывает, что тип данных неопределен и может быть заменен на любой тип в контексте использования
     */
    public ResponseEntity<?> createUser(RegisterUserRequestDto dto) {
        System.out.println("Создаем юзера");
        if (userRepository.existsUserByLogin(dto.getLogin()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("We have this login in database: ");
        else {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());

            User createUser = User.builder()
                    .login(dto.getLogin())
                    .password(encodedPassword)
                    .email(dto.getEmail())
                    .build();
            userRepository.save(createUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
        }
    }

    public ResponseEntity<User> allowInTori(String login, String enteredPassword) {
        System.out.println("Проверяем соответствие пароля");
        User allowInUser = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("We dont have this login in database: " + login));

        if (passwordEncoder.matches(enteredPassword, allowInUser.getPassword())) {
            return ResponseEntity.ok(allowInUser);

        }
//        if (allowInUser.getLogin().equals(enteredPassword)){
//            return ResponseEntity.ok(allowInUser);
//        }
        // Пароли совпадают, возвращаем успешный статус со сущностью пользователя
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // TODO: 23.10.2023  пока не понимаю что мы тут менять будем.
    // Пока все поменяем кроме внешнего ключа (логин)
    // парни должны помнить про свой айди как пользователя
    public ResponseEntity<User> updateUserLogin(Integer id, String login, String password, String email) {
        System.out.println("Обновляем данные о пользователе");
        Optional<User> currentUser = userRepository.findById(id);
        if (currentUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            User existingUser = currentUser.get();
            existingUser.setLogin(login); // поменяли на новый блатной логин
            existingUser.setPassword(passwordEncoder.encode(password));
            existingUser.setEmail(email);

            userRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        }
    }
}