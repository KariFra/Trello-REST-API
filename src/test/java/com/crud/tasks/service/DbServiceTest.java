package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import javafx.beans.binding.When;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService service;

    @Mock
    private TaskRepository repository;

    @Test
    public void testGetTasks(){
        //Given
        Task task1 = new Task(1L,"task1","task1 content");
        Task task2 = new Task(2L,"task2","task2 content");
        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);

        when(repository.findAll()).thenReturn(list);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(( task1 )));

        //When
        List<Task> listFromService = service.getAllTasks();
        Optional<Task> taskFromService = service.getTaskWithID(1L);

        //Than
        assertEquals("task2 content",listFromService.get(1).getContent());
        assertEquals(Optional.of(task1),taskFromService);

    }
    @Test
    public void testSave(){
        //Given
        Task task = new Task(1L,"task","task content");

        when(repository.save(any(Task.class))).thenReturn(task);
        //When
        Task created = service.saveTask(task);

        //Than
       assertEquals("task",created.getTitle());
    }



}