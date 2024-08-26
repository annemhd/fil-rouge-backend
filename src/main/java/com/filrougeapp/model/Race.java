package com.filrougeapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "race") // spécifie que cette entité est mappée à la table "race"
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // déclare une colonne pour stocker la vitesse moyenne d'une course
    @Column(name = "average_speed") 
    private Double averageSpeed;

    // Déclare une colonne pour stocker la distance parcourue pendant une course
    @Column(name = "distance_covered") 
    private Double distanceCovered;

    // déclare une colonne pour stocker le temps passé pendant une course.
    @Column(name = "time_spent") 
    private Double timeSpent;

    // déclare une colonne pour stocker la vitesse de rotation des roues pendant une course.
    @Column(name = "wheel_rotation_speed") 
    private Double wheelRotationSpeed;

    // déclare une colonne pour stocker la date et l'heure de création de l'entité
    @CreationTimestamp 
    @Column(name = "created_at") 
    private LocalDateTime createdAt; // utilise LocalDateTime pour stocker la date et l'heure
}