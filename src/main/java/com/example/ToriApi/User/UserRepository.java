package com.example.ToriApi.User;

//import com.example.ToriApi.User.Entityes.FriendsRequests;
import com.example.ToriApi.User.Entityes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Bulat Sharapov
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);

    List<User> findByLoginIn(List<String> logins);
    Optional<User> findById(Integer id);
    boolean existsUserByLogin(String login);
//    @Transactional
//    @Modifying
//    @Query("UPDATE User u SET u.friendsRequests = :newFriendsRequests WHERE u.id = :userId")
//    void updateFriendsRequests(Integer userId, List<String> newFriendsRequests);
}
