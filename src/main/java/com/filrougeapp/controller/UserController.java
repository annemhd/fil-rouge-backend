package com.filrougeapp.controller;

import com.filrougeapp.model.User;
import com.filrougeapp.model.Race;
import com.filrougeapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // indique que cette classe est un contrôleur REST
@RequestMapping("/users") // spécifie la route de base pour toutes les méthodes de ce contrôleur
public class UserController {

    @Autowired // injection automatique du dépôt d'utilisateurs
    private UserRepository userRepository;

    // méthode pour récupérer tous les utilisateurs stockés dans la base de données
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // méthode pour récupérer un utilisateur spécifique par son ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // méthode pour créer un nouvel utilisateur
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // méthode pour mettre à jour un utilisateur existant
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstname(userDetails.getFirstname());
                    user.setLastname(userDetails.getLastname());
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(userDetails.getPassword());
                    user.setRole(userDetails.getRole());
                    User updatedUser = userRepository.save(user);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // méthode pour supprimer un utilisateur par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // méthode pour récupérer toutes les courses associées à un utilisateur
    // spécifique par son ID
    @GetMapping("/{userId}/races")
    public ResponseEntity<List<Race>> getRacesByUserId(@PathVariable Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Race> races = userOptional.get().getRaces();
            return ResponseEntity.ok(races);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/avatar/{avatar}")
    public ResponseEntity<String> updateAvatar(@PathVariable Integer id, @PathVariable Integer avatar) {
        // Fetch the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User non existant: " + id));

        user.setAvatar(avatar);
        userRepository.save(user);

        return ResponseEntity.ok("Votre avatar a bien été mis à jour");
    }
}