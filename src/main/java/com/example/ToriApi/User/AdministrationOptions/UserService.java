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

/**
 * @author Bulat Sharapov
 */
@Service
@AllArgsConstructor

public class UserService {
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserAdministrationController.class);


//        try {
//            if (getUserByLogin(user.getLogin()) != null) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).body("We have this login in database: ");
//            }
//            User createUser = userRepository.save(user);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
//        } catch (Exception exception) {
//            logger.error("Internal server error", exception);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
    /**
     * @param login айди юзера
     * @return Возвращаем юзера по айди.
     * Один из способов применения - при отправке фото друзьям.
     * Мб доработать надо будет
     * ʕ ᵔᴥᵔ ʔ Переделал и ща мы ищем не по айди, а по логину
     */
    public ResponseEntity<User> getUserByLogin(String login) {
        System.out.println("Ищем по логину");
        try {
            User user = userRepository.findByLogin(login)
                    .orElseThrow(() -> new ResourceNotFoundException("We dont have this login in database: " + login));
            return ResponseEntity.ok().body(user);
        } catch (ResourceNotFoundException exception) {
            logger.error("Error while getting user", exception);
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            logger.error("Internal server error", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @GetMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
//        System.out.println("Ищем по айдишнику");
//        try {
//            User user = userRepository.findById(id)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
//            return ResponseEntity.ok().body(user);
//        } catch (ResourceNotFoundException exception) {
//            logger.error("Error while getting user", exception);
//            return ResponseEntity.notFound().build();
//        } catch (Exception exception) {
//            logger.error("Internal server error", exception);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

}
