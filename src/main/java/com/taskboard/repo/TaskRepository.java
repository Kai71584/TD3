package com.taskboard.repo;

import com.taskboard.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface qui hérite de JpaRepository.
 * Fournit toutes les opérations CRUD (create, read, update, delete)
 * sans écrire une seule ligne de code SQL.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {}
