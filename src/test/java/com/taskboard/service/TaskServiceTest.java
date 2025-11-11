package com.taskboard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.taskboard.model.Task;
import com.taskboard.repo.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test unitaire pur : - on isole TaskService en simulant TaskRepository (pas de DB réelle) -
 * Mockito crée des "mocks" pour contrôler les retours et vérifier les appels
 */
@ExtendWith(MockitoExtension.class) // active Mockito pour JUnit 5
class TaskServiceTest {

  @Mock TaskRepository repository; // faux dépôt (aucune base utilisée)

  @InjectMocks TaskService service; // le service reçoit le mock comme dépendance

  @Test
  void addTask_shouldSaveWithGivenTitle() {
    // Arrange : on définit le retour du repository.save(...)
    when(repository.save(any(Task.class)))
        .thenAnswer(invocation -> invocation.getArgument(0)); // renvoie l'objet passé

    // Act : on appelle la méthode métier
    Task saved = service.addTask("Lire chapitre 1");

    // Assert : vérifications
    assertEquals("Lire chapitre 1", saved.getTitle()); // le titre a été conservé
    assertFalse(saved.isCompleted()); // par défaut false
    verify(repository).save(any(Task.class)); // save(...) a bien été appelé
  }

  @Test
  void listAll_shouldReturnAllTasks() {
    when(repository.findAll()).thenReturn(List.of(new Task("A", false), new Task("B", true)));

    List<Task> all = service.listAll();

    assertEquals(2, all.size());
    assertEquals("A", all.get(0).getTitle());
    assertTrue(all.get(1).isCompleted());
    verify(repository).findAll();
  }

  @Test
  void findById_shouldReturnPresentOptional() {
    when(repository.findById(42L)).thenReturn(Optional.of(new Task("Réviser TD3", false)));

    Optional<Task> found = service.findById(42L);

    assertTrue(found.isPresent());
    assertEquals("Réviser TD3", found.get().getTitle());
  }
}
