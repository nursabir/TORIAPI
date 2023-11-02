package com.example.ToriApi.User.Entityes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * @author Bulat Sharapov
 */
@Table(name = "tori_user", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    @Schema(example = "0")
    private Integer id;

    //    @Id
    @Column(name = "login", unique = true)
    @Schema(example = "john_doe")
    private String login;
    @Column(name = "password")
    @Schema(example = "secretpassword")
    private String password;
    @Column(name = "email")
    @Schema(example = "john@example.com")
    private String email;


//    @Column(name = "friends_array", columnDefinition = "INTEGER[]")
//    @Convert(converter = IntegerListConverter.class)
//    private List<Integer> friends;
////    private String friends;
//
//    @Column(name = "friends_requests", columnDefinition = "INTEGER[]")
//    @Convert(converter = IntegerListConverter.class)
//    private List<Integer> friendsRequests;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends_requests",
            joinColumns = {@JoinColumn(name = "id_user_sender")},
            inverseJoinColumns = {@JoinColumn(name = "id_user")}
    )
    private Set<User> myRequests;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends_requests",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_user_sender")}
    )
    private Set<User> friendsRequests;


//mappedBy сообщает Hibernate,
// kакое поле в связанной сущности используется для хранения информации об отношении.

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_user_friend")}
    )
    private Set<User> friends;


//    @OneToMany(mappedBy = "sender")
//    private List<User> sentFriendRequests;

}

