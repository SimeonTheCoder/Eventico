package com.eventico.repo;

import com.eventico.model.entity.Event;
import com.eventico.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
    @Transactional
    @Modifying
    @Query("update Event e set e.participants = ?1 where e.id = ?2")
    int updateParticipantsById(List<User> participants, Long id);

    void deleteById(Long id);
}
