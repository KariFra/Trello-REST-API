package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade facade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception{
        // Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(facade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When & Than
        mockMvc.perform(get("/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$",hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception{
       //Given
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1","first list",true));

        List<TrelloBoardDto> trelloBoard = new ArrayList<>();
        trelloBoard.add(new TrelloBoardDto("1","test task",trelloList));

        when(facade.fetchTrelloBoards()).thenReturn(trelloBoard);

        //When & Than
        mockMvc.perform(get("/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id",is("1")))
                .andExpect(jsonPath("$[0].name",is("test task")))
                .andExpect(jsonPath("$[0].lists",hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].name", is("first list")));
    }
    @Test
    public void shouldCreateTrelloCard() throws Exception{
        //Given
        TrelloCardDto newCard = new TrelloCardDto(
                "Test",
                "Test description",
                "1",
                "top");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto(
                "323",
                "test",
                "http://test.com");

        when(facade.createCard(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(createdCard);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(newCard);

        //When & Than
        mockMvc.perform(post("/v1/trello/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id",is("323")))
                .andExpect(jsonPath("$.name",is("test")))
                .andExpect(jsonPath("$.shortUrl",is("http://test.com")));


    }
}