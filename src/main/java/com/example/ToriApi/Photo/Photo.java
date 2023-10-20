package com.example.ToriApi.Photo;

import com.example.ToriApi.StringListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Bulat Sharapov
 */
@Entity
@Getter
@Setter
@Table(name = "Photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_photo")
    private Integer id;
    @Column(name = "user_id_from") // от кого фотка
    private String userIdFrom;
    @Column(name = "user_id_to")
    @Convert(converter = StringListConverter.class)
    // TODO: 07.10.2023 ЕСТЬ идея не использовать эту сущность. А просто в бд по user_id_from брать
    private String[] userIdTo;
    @Column(name = "image", columnDefinition = "bytea")
    private Byte image;

}
