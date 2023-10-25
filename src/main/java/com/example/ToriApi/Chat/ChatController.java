package com.example.ToriApi.Chat;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/chat")
public class ChatController {
    private ChatService chatService;

    /**
     *
     * @param loginFrom логин клиента, который хочет отправить комментарий
     * @param loginTo логин клиента, которому хотят отправить комментарий
     * @param message собственно комментарий
     */
    @PostMapping("{loginFrom}/sendMessage/{loginTo}")
    public ResponseEntity<?> sendMessage(@PathVariable String loginFrom, @PathVariable String loginTo, @RequestBody String message){
        return chatService.sendMessage(loginFrom, loginTo, message);
    }

    /**
     *
     * @param loginTo логин клиента, который получает комментарий
     */
    @GetMapping("{loginTo}/getMessages")
    public ResponseEntity<?> getMessages(@PathVariable String loginTo){
        return chatService.getMessages(loginTo);
    }



}
