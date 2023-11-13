package com.example.ToriApi.Photo;

import com.example.ToriApi.StringListConverter;
import com.example.ToriApi.User.Entityes.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Bulat Sharapov
 */
@Entity
@Getter
@Setter
@Table(name = "Photo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    // берется из снаружи
    @Id
    @Column(name = "uniqIdentifier_photo")
    private String uniqIdentifier;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "photo_recipients",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id")
    )
    private List<User> recipients;

}
