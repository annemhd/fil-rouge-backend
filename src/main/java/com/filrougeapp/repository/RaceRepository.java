package com.filrougeapp.repository;

import com.filrougeapp.model.Race;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Integer> {

    // méthode pour trouver une course par sa vitesse moyenne et renvoie un optional pour gérer le cas où aucune course ne correspond
    Optional<Race> findByAverageSpeed(Double averageSpeed);

    // méthode pour trouver une course par la distance parcourue et renvoie un optional pour gérer le cas où aucune course ne correspond
    Optional<Race> findByDistanceCovered(Double distanceCovered);

    // méthode pour trouver une course par le temps passé et renvoie un optional pour gérer le cas où aucune course ne correspond
    Optional<Race> findByTimeSpent(Double timeSpent);

    // méthode pour trouver une course par la vitesse de rotation des roues et renvoie un optional pour gérer le cas où aucune course ne correspond
    Optional<Race> findByWheelRotationSpeed(Double wheelRotationSpeed);

    List<Race> findByUserId(Integer userId);
}