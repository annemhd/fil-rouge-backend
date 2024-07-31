package com.filrougeapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.filrougeapp.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
