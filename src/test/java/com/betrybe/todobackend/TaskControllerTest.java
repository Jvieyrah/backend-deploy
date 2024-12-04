package com.betrybe.todobackend;

import com.betrybe.todobackend.controllers.TaskController;
import com.betrybe.todobackend.models.entities.Task;
import com.betrybe.todobackend.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateTask() {
        Task taskToCreate = new Task();
        when(taskService.create(taskToCreate)).thenReturn(taskToCreate);

        ResponseEntity<Task> response = taskController.create(taskToCreate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskToCreate, response.getBody());
        verify(taskService, times(1)).create(taskToCreate);
    }

    @Test
    void testGetAllTasks() {
        List<Task> taskList = new ArrayList<>();
        when(taskService.getAll()).thenReturn(taskList);

        ResponseEntity<List<Task>> response = taskController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskList, response.getBody());
        verify(taskService, times(1)).getAll();
    }

    @Test
    void testGetTaskByIdExisting() {
        Long taskId = 1L;
        Task task = new Task();
        when(taskService.getById(taskId)).thenReturn(Optional.of(task));

        ResponseEntity<Task> response = taskController.getById(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).getById(taskId);
    }

    @Test
    void testGetTaskByIdNonExistent() {
        Long taskId = 1L;
        when(taskService.getById(taskId)).thenReturn(Optional.empty());

        ResponseEntity<Task> response = taskController.getById(taskId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(taskService, times(1)).getById(taskId);
    }

    @Test
    void testUpdateTaskExisting() {
        Long taskId = 1L;
        Task updatedTask = new Task();
        when(taskService.update(taskId, updatedTask)).thenReturn(Optional.of(updatedTask));

        ResponseEntity<Task> response = taskController.update(taskId, updatedTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTask, response.getBody());
        verify(taskService, times(1)).update(taskId, updatedTask);
    }

    @Test
    void testUpdateTaskNonExistent() {
        Long taskId = 1L;
        Task fakeTask = new Task();
        when(taskService.update(eq(taskId), any(Task.class))).thenReturn(Optional.empty());


        ResponseEntity<Task> response = taskController.update(taskId, fakeTask);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(taskService, times(1)).update(taskId, fakeTask);
    }

    @Test
    void testDeleteTask() {
        Long taskId = 1L;

        ResponseEntity<?> response = taskController.delete(taskId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).delete(taskId);
    }
}
