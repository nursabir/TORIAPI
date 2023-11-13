package com.example.ToriApi.Photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Bulat Sharapov
 */
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    Optional<Photo> findByUniqIdentifier(String uniqIdentifier);

    boolean deleteByUniqIdentifier(String uniqIdentifier);


}