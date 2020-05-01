package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailSchedulerTest {
    @InjectMocks
    private EmailScheduler scheduler;

    @Mock
    private TaskRepository repository;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig config;

    @Test
    public void testSendInformationEmail(){
        //Given
        when(repository.count()).thenReturn((long) 2);
        when(config.getAdminMail()).thenReturn("test@test.com");


        //When
        scheduler.sendInformationEmail();

        //Than
        Mockito.verify(simpleEmailService).send(any(Mail.class));

    }


}