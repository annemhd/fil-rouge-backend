package com.filrougeapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.filrougeapp.model.User;

// déclaration de l'interface UserRepository qui étend JpaRepository
public interface UserRepository extends JpaRepository<User, Integer> {

    // méthode pour trouver un utilisateur par son nom d'utilisateur (username)
    Optional<User> findByUsername(String username);

    // annotation @Query pour définir une requête JPQL personnalisée
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.races r WHERE u.id = :userId")
    
    // méthode pour récupérer un utilisateur ainsi que ses courses (races) en fonction de son identifiant (userId)
    Optional<User> findUserWithRacesByUserId(@Param("userId") Integer userId);
}