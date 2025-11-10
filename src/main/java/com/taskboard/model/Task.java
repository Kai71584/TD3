package com.taskboard.model;

import jakarta.persistence.*;

/**
 * Entité JPA représentant une tâche.
 * Elle est persistée automatiquement dans la base H2.
 */
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean completed = false;

    // Constructeur vide obligatoire pour JPA
    public Task() {}

    // Constructeur pratique pour les tests ou initialisations
    public Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    // Accesseurs
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
    public void setTitle(String title) { this.title = title; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
