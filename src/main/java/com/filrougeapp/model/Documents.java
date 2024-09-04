package com.filrougeapp.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "documents")
public class Documents {

    // annotation pour spécifier que cet attribut est la clé primaire de l'entité
    @Id
    
    // annotation pour spécifier que la valeur de la clé primaire sera générée automatiquement par la base de données
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // annotation pour spécifier les détails de la colonne associée dans la table de la base de données
    @Column(length = 512, nullable = false, unique = true)
    private String name;

    private Long size;

    private Date date;

    // annotation pour indiquer que cette colonne peut contenir de grandes quantités de données binaires
    @Lob
    
    // annotation pour définir le type de colonne pour le stockage des données binaires longues dans la base de données
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;
}