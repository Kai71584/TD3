package com.taskboard.service;

import com.taskboard.model.Task;
import com.taskboard.repo.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Couche intermédiaire entre le contrôleur et la base de données. Contient les règles métier du
 * programme.
 */
@Service
public class TaskService {
  private final TaskRepository repository;

  public TaskService(TaskRepository repository) {
    this.repository = repository;
  }

  /** Crée une nouvelle tâche avec le titre donné. */
  public Task addTask(String title) {
    return repository.save(new Task(title, false));
  }

  /** Retourne toutes les tâches. */
  public List<Task> listAll() {
    return repository.findAll();
  }

  /** Cherche une tâche par ID. */
  public Optional<Task> findById(Long id) {
    return repository.findById(id);
  }

  /** Supprime une tâche par ID. */
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
