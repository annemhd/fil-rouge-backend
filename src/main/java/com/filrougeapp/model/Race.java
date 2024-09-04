package com.filrougeapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;

// annotation pour générer automatiquement les méthodes getter, setter, equals, hashCode, et toString
@Data

// annotation pour générer un constructeur sans arguments
@NoArgsConstructor

// annotation pour générer un constructeur avec tous les arguments
@AllArgsConstructor

// annotation pour générer un constructeur de construction pour la création d'instances immuables
@Builder

// annotation pour indiquer que cette classe est une entité JPA et sera mappée à une table dans la base de données
@Entity

// annotation pour spécifier le nom de la table dans la base de données à laquelle cette entité est mappée
@Table(name = "race")
public class Race {

    // annotation pour spécifier que cet attribut est la clé primaire de l'entité
    @Id

    // annotation pour spécifier que la valeur de la clé primaire sera générée automatiquement par la base de données
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Annotation pour spécifier les détails de la colonne associée dans la table de la base de données
    @Column(name = "average_speed")
    private Double averageSpeed;

    @Column(name = "distance_covered")
    private Double distanceCovered;

    @Column(name = "time_spent")
    private Double timeSpent;

    @Column(name = "wheel_rotation_speed")
    private Double wheelRotationSpeed;

    // annotation pour générer automatiquement la date et l'heure de création de l'entité
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // annotation pour définir une relation ManyToOne avec l'entité User, avec une récupération paresseuse
    @ManyToOne(fetch = FetchType.LAZY)

    // annotation pour définir la clé étrangère associée à la colonne "user_id" dans la table "race"
    @JoinColumn(name = "user_id", nullable = false)

    // annotation pour éviter les boucles infinies lors de la sérialisation JSON, en ignorant la référence inversée
    @JsonBackReference
    private User user;
}