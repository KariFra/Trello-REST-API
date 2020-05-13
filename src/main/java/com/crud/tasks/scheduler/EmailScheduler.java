package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT="Tasks: once a day email";

    @Autowired
    private SimpleEmailService simpleEmailService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AdminConfig adminConfig;


    String info = "";

    @Scheduled(cron="0 0 10 * * *")
    public void sendInformationEmail(){
        long size = taskRepository.count();
        String end = "tasks.";
        if(size == 1){
            end = "task.";
        }
        info ="Currently in database you've got: "+ size + " " + end;
        simpleEmailService.sendScheduledMail(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                info,
                " "));
    }





}
