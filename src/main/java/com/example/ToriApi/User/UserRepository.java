package com.example.ToriApi.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Bulat Sharapov
 */
public interface UserRepository extends JpaRepository<User, Integer> {
   Optional<User> findByLogin(String id);

}
