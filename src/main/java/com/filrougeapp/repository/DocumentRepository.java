package com.filrougeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.filrougeapp.model.Documents;

public interface DocumentRepository extends JpaRepository<Documents, Integer> {
    @Query("SELECT d FROM Documents d ORDER BY d.date DESC")
    List<Documents> findAll();
}
