package com.example.UserService.Repository;

import com.example.UserService.models.Session;
import com.example.UserService.models.SessionStatus;
import com.example.UserService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
    Optional<Session> findByToken(String token);

    Optional<Session> findByUserAndSessionStatus(User user, SessionStatus sessionStatus);

}
