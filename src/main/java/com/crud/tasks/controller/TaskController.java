package com.crud.tasks.controller;


import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper mapper;

    @GetMapping(value = "getTasks")
    public List<TaskDto> getTasks() {
        return mapper.mapToTaskDtoList(service.getAllTasks());
    }

    @DeleteMapping(value = "deleteTask/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        service.deleteTask(taskId);
    }

    @PutMapping(value = "updateTask")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return mapper.mapToTaskDto(service.saveTask(mapper.mapToTask(taskDto)));
    }

    @PostMapping(value = "createTask",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Task createTask(@RequestBody TaskDto taskDto) {
        return service.saveTask(mapper.mapToTask(taskDto));
    }

    @GetMapping(value = "getTask/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        return mapper.mapToTaskDto(service.getTaskWithID(taskId)
                .orElseThrow(TaskNotFoundException::new));
    }

}
