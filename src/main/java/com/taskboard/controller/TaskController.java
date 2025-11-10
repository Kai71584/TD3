package com.taskboard.controller;

import com.taskboard.model.Task;
import com.taskboard.service.TaskService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST exposant les opérations principales :
 * - POST /tasks : créer une tâche
 * - GET /tasks  : lister toutes les tâches
 * - GET /tasks/{id} : obtenir une tâche
 * - DELETE /tasks/{id} : supprimer une tâche
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task t) {
        Task saved = service.addTask(t.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Task> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
