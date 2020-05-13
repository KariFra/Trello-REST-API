package com.crud.tasks.service;


import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    public String buildingTrelloCardEmail(String message){
        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","http://localhost:8888/tasks_frontend");
        context.setVariable("button","Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye","Your Task App");
        context.setVariable("detailsPartOne",adminConfig.getCompanyName());
        context.setVariable("detailsPartTwo",adminConfig.getCompanyMail());
        context.setVariable("detailsPartThree",adminConfig.getCompanyGoal());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
