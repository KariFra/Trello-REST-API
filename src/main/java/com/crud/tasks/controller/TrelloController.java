package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() throws BoardNotFoundException {

//        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
//


        List<Optional<TrelloBoardDto>> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(t-> t.getName() != null && t.getId() != null)
                .filter(t-> t.getName().contains("Kodilla"))
                .forEach(System.out::println);

    }
}
