package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {

    @Autowired
    private TaskMapper mapper;

    @Test
    public void testMapTask(){
        //Given
        Task task = new Task(
                1L,
                "task",
                "task content"
        );
        //When
        TaskDto taskDto = mapper.mapToTaskDto(task);
        Task mappedTask = mapper.mapToTask(taskDto);

        //Than
        assertEquals("task",taskDto.getTitle());
        assertEquals("task content",taskDto.getContent());
    }


    @Test
    public void testMapListDto(){
        //Given
        Task taskDtoOne = new Task(1L,"task1","task content1");
        Task taskDtoTwo = new Task(2L, "task2", "task content2");
        List<Task> list = new ArrayList<>();
        list.add(taskDtoOne);
        list.add(taskDtoTwo);

        //When
        List<TaskDto> listTask = mapper.mapToTaskDtoList(list);

        //Than
        assertEquals("task content1",listTask.get(0).getContent());
        assertEquals("task content2",listTask.get(1).getContent());

    }

}



