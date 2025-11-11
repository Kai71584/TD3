package com.taskboard.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.taskboard.model.Task;
import com.taskboard.repo.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test d'intégration : - démarre un vrai contexte Spring (@SpringBootTest) - utilise H2 en mémoire
 * (config application.yaml) - MockMvc simule des requêtes HTTP sans serveur externe
 */
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

  @Autowired MockMvc mockMvc; // simulateur d'appels HTTP
  @Autowired TaskRepository repository; // vrai repo (H2 in-memory)

  @BeforeEach
  void clean() {
    repository.deleteAll();
  } // base propre à chaque test

  @Test
  void postTasks_shouldCreateAndReturn201() throws Exception {
    // POST /tasks avec un JSON minimal { "title": "Faire TD3" }
    mockMvc
        .perform(
            post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Faire TD3\"}"))
        .andExpect(status().isCreated()) // statut 201
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.title", is("Faire TD3")))
        .andExpect(jsonPath("$.completed", is(false)));
  }

  @Test
  void getTasks_shouldReturnArrayWithInsertedItem() throws Exception {
    // On insère directement une Task via le repository (équivaut à avoir déjà des données)
    repository.save(new Task("Nettoyer projet", false));

    // GET /tasks → on attend un tableau JSON de taille 1 avec le bon titre
    mockMvc
        .perform(get("/tasks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].title", is("Nettoyer projet")));
  }

  @Test
  void getUnknownTask_shouldReturn404() throws Exception {
    mockMvc.perform(get("/tasks/999")).andExpect(status().isNotFound());
  }

  @Test
  void deleteTask_shouldReturn204() throws Exception {
    Long id = repository.save(new Task("À supprimer", false)).getId();

    mockMvc.perform(delete("/tasks/" + id)).andExpect(status().isNoContent());

    // La tâche ne doit plus exister
    mockMvc.perform(get("/tasks/" + id)).andExpect(status().isNotFound());
  }
}
