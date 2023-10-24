package com.example.ToriApi.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Bulat Sharapov
 */
public interface UserRepository extends JpaRepository<User, Integer> {
   Optional<User> findByLogin(String id);

//   @Modifying
//   @Query("UPDATE User u SET u.friendsRequests = ARRAY_REMOVE(u.friendsRequests, :login) WHERE u.id = :userId")
//   void removeFriendRequestByIdAndLogin(@Param("userId") int userId, @Param("login") String login);

   @Modifying
   @Query("UPDATE User u SET u.friendsRequests = (SELECT fr FROM User u JOIN u.friendsRequests fr WHERE u.login = :id AND fr <> :login) WHERE u.login = :id")
   void removeFromFriendsRequestsById(@Param("id") Integer id, @Param("login") String login);

}
