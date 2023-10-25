package com.example.ToriApi.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Bulat Sharapov
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
    Optional<User> findById(Integer id);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.friendsRequests = :newFriendsRequests WHERE u.id = :userId")
    void updateFriendsRequests(Integer userId, List<String> newFriendsRequests);
}
