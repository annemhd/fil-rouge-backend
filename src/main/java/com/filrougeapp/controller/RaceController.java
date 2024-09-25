package com.filrougeapp.controller;

import com.filrougeapp.model.Race;
import com.filrougeapp.model.User;
import com.filrougeapp.repository.RaceRepository;
import com.filrougeapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/races")
public class RaceController {

    @Autowired  // injection automatique du dépôt de courses
    private RaceRepository raceRepository;

    @Autowired  // injection automatique du dépôt d'utilisateurs
    private UserRepository userRepository;

    // méthode pour récupérer toutes les courses stockées dans la base de données
    @GetMapping
    public List<Race> getAllRaces() {
        return raceRepository.findAll(); 
    }

    // méthode pour récupérer une course spécifique par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Race> getRaceById(@PathVariable Integer id) {
        Optional<Race> race = raceRepository.findById(id);
        return race.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // méthode pour créer une nouvelle course
    @PostMapping
    public ResponseEntity<?> createRace(@RequestBody Race race) {
        if (race == null || race.getUser() == null || race.getUser().getId() == null) {
            return ResponseEntity.badRequest().body("Invalid race data or missing user ID.");
        }

        Optional<User> userOptional = userRepository.findById(race.getUser().getId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + race.getUser().getId());
        }
        race.setUser(userOptional.get());
        
        Race savedRace = raceRepository.save(race);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRace);
    }

    // méthode pour mettre à jour une course existante
    @PutMapping("/{id}")
    public ResponseEntity<Race> updateRace(@PathVariable Integer id, @RequestBody Race raceDetails) {
        return raceRepository.findById(id)
                .map(race -> {
                    race.setAverageSpeed(raceDetails.getAverageSpeed());
                    race.setDistanceCovered(raceDetails.getDistanceCovered());
                    race.setTimeSpent(raceDetails.getTimeSpent());
                    race.setWheelRotationSpeed(raceDetails.getWheelRotationSpeed());
                    Race updatedRace = raceRepository.save(race);
                    return ResponseEntity.ok(updatedRace);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // méthode pour supprimer une course par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRace(@PathVariable Integer id) {
        return raceRepository.findById(id)
                .map(race -> {
                    raceRepository.delete(race);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}