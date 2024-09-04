package com.filrougeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.filrougeapp.model.Documents;

// déclaration de l'interface DocumentRepository qui étend JpaRepository
public interface DocumentRepository extends JpaRepository<Documents, Integer> {

    // annotation @Query pour définir une requête JPQL personnalisée
    @Query("SELECT d FROM Documents d ORDER BY d.date DESC")
    
    // méthode pour récupérer tous les documents, triés par date de manière décroissante
    List<Documents> findAll();
}