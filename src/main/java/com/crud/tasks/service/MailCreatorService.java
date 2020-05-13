package com.crud.tasks.service;


import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;



    public String buildingTrelloCardEmail(String message){
        ArrayList<String> functionality = new ArrayList<>();
        functionality.add("Manage your tasks.");
        functionality.add("Connect with trello.");
        functionality.add("Send your tasks to trello.");

        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","http://localhost:8888/tasks_frontend");
        context.setVariable("button","Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye","Your Task App");
        context.setVariable("detailsPartOne",adminConfig.getCompanyName());
        context.setVariable("detailsPartTwo",adminConfig.getCompanyMail());
        context.setVariable("detailsPartThree",adminConfig.getCompanyGoal());
        context.setVariable("show_button", false);
        context.setVariable("is_friend",true);
        context.setVariable("config_name",adminConfig);
        context.setVariable("app_functionality",functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildingSchedulerMail(String message){
        ArrayList<String> goodbyePhrase = new ArrayList<>();
        goodbyePhrase.add("Adios");
        goodbyePhrase.add("Hasta lavista");
        goodbyePhrase.add("Buenos Dias");
        goodbyePhrase.add("Buenos Aires");

        String joke = "Code is like an onion - the more layers you peel back, the more you cry.";

        Context context = new Context();
        context.setVariable("subject","Tasks: once a day email");
        context.setVariable("message_scheduler",message);
        context.setVariable("tasks_url","http://localhost:8888/tasks_frontend");
        context.setVariable("button","Visit website");
        context.setVariable("show_details", false);
        context.setVariable("joke",joke );
        context.setVariable("is_sad",true);
        context.setVariable("goodbye",goodbyePhrase);
        context.setVariable("details",adminConfig.getCompanyName());
        return templateEngine.process("mail/scheduled-mail", context);

    }
}
