package com.example.ToriApi.Chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Bulat Sharapov
 */
@Table(name = "chat")
@Getter
@Setter
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Integer id;
    @Column(name = "login_from")
    private String loginFrom;
    @Column(name = "login_to")
    private String loginTo;
    @Column(name = "message")
    private String message;


}
