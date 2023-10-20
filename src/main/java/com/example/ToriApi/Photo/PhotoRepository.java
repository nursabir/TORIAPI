package com.example.ToriApi.Photo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Bulat Sharapov
 */
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}