package com.example.ToriApi.User;

import com.example.ToriApi.StringListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * @author Bulat Sharapov
 */
@Table(name = "tori_user", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
@Entity
@Getter
@Setter
public class User {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_user")
//    private Integer id;
    @Id
    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;

    @Column(name = "friends_array", columnDefinition = "VARCHAR[]")
    @Convert(converter = StringListConverter.class)
    private List<String> friends;

    @Column(name = "friends_requests", columnDefinition = "VARCHAR[]")
    @Convert(converter = StringListConverter.class)
    private List<String> friendsRequests;

}

