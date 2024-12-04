package com.betrybe.todobackend;

import com.betrybe.todobackend.models.entities.Task;
import com.betrybe.todobackend.models.repositories.TaskRepository;
import com.betrybe.todobackend.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        taskService = new TaskService(taskRepository);
    }

    @Test
    void testCreateTask() {
        Task taskToCreate = new Task();
        when(taskRepository.save(taskToCreate)).thenReturn(taskToCreate);

        Task createdTask = taskService.create(taskToCreate);

        assertNotNull(createdTask);
        verify(taskRepository, times(1)).save(taskToCreate);
    }

    @Test
    void testGetAllTasks() {
        List<Task> taskList = new ArrayList<>();
        when(taskRepository.findAll()).thenReturn(taskList);

        List<Task> retrievedTasks = taskService.getAll();

        assertEquals(taskList, retrievedTasks);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById() {
        Long taskId = 1L;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Optional<Task> retrievedTask = taskService.getById(taskId);

        assertTrue(retrievedTask.isPresent());
        assertEquals(task, retrievedTask.get());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void testUpdateTask() {
        Long taskId = 1L;
        Task existingTask = new Task();
        Task updatedTask = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(updatedTask)).thenReturn(updatedTask);

        Optional<Task> result = taskService.update(taskId, updatedTask);

        assertTrue(result.isPresent());
        assertEquals(updatedTask, result.get());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(updatedTask);
    }

    @Test
    void testUpdateTaskNonExistent() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.update(taskId, new Task());

        assertFalse(result.isPresent());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void testDeleteTask() {
        Long taskId = 1L;

        taskService.delete(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
