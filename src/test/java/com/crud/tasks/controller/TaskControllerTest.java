package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.Matchers.is;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper mapper;

    @Test
    public void shouldGetTasks() throws Exception{
        //Given
    List<TaskDto> list = new ArrayList<>();
    list.add(new TaskDto(1L,"task","content"));
   when(mapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(list);

        //When & Than
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].title",is("task")))
                .andExpect(jsonPath("$[0].content",is("content")))
                .andExpect(jsonPath("$",hasSize(1)));

    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        Task task = new Task(1L,"task","content");

        when(service.getTaskWithID(1L)).thenReturn(Optional.of(task));
        when(mapper.mapToTaskDto(task)).thenReturn(new TaskDto(1L,"task1","content1"));

        //When & Than
        mockMvc.perform(get("/v1/task/getTask?taskId=1")
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title",is("task1")))
                .andExpect(jsonPath("$.content",is("content1")));
    }

    @Test
    public void noTask() throws Exception {
        //Given
        when(service.getTaskWithID(1L)).thenReturn(Optional.of(new Task()));
        when(mapper.mapToTaskDto(new Task())).thenReturn(new TaskDto());

        //When & Than
        mockMvc.perform(get("/v1/task/getTask?taskId=1")
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200));

    }

    @Test
    public void shouldSaveTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L,"task","content");
        Task task = new Task(1L,"task","content");


        when(service.saveTask(mapper.mapToTask(taskDto))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);
        //When & Than
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title",is("task")))
                .andExpect(jsonPath("$.content",is("content")));

    }

    @Test
    public void shouldDeleteTask() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/task/deleteTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));


        Mockito.verify(service).deleteTask(1L);
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(1L,"task","content");
        TaskDto taskDto = new TaskDto(1L,"task","content");

        when(mapper.mapToTaskDto(service.saveTask(mapper.mapToTask(any(TaskDto.class))))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);
        //When & Than
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title",is("task")))
                .andExpect(jsonPath("$.content",is("content")));
    }


}