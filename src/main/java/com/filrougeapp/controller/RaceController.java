package com.filrougeapp.controller;

import com.filrougeapp.model.Race;
import com.filrougeapp.model.User;
import com.filrougeapp.repository.RaceRepository;
import com.filrougeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/races")
public class RaceController {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Race> getAllRaces() {
        return raceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Race> getRaceById(@PathVariable Integer id) {
        Optional<Race> race = raceRepository.findById(id);
        return race.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Race> createRace(@RequestBody Race race) {
        Optional<User> user = userRepository.findById(race.getUser().getId());
        if (user.isPresent()) {
            race.setUser(user.get());
            Race savedRace = raceRepository.save(race);
            return ResponseEntity.ok(savedRace);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

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
