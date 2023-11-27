package com.eventico.repo;

import com.eventico.model.entity.Event;
import com.eventico.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.participationEvents = ?1 where u.id = ?2")
    void updateParticipationEventsById(List<Event> participationEvents, Long id);
    @Transactional
    @Modifying
    @Query("update User u set u.points = ?1 where u.id = ?2")
    void updatePointsById(int points, Long id);

    User findByUsername(String username);
}
