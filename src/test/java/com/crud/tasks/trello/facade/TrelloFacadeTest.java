package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import javafx.beans.binding.When;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade facade;

    @Mock
    private TrelloService service;

    @Mock
    private TrelloValidator validator;

    @Mock
    private TrelloMapper mapper;

    @Test
    public void shouldFetchEmptyList(){
        //Given
        List<TrelloListDto> dtoList = new ArrayList<>();
        dtoList.add(new TrelloListDto("1","list one",true));

        List<TrelloBoardDto> boardDtoList = new ArrayList<>();
        boardDtoList.add(new TrelloBoardDto("1","item one",dtoList));

        List<TrelloList> mappedList = new ArrayList<>();
        mappedList.add(new TrelloList("1","list one",true));

        List<TrelloBoard> mappedBoard = new ArrayList<>();
        mappedBoard.add(new TrelloBoard("1","item one",mappedList));

        when(service.fetchTrelloBoards()).thenReturn(boardDtoList);
        when(mapper.mapToTrelloBoard(boardDtoList)).thenReturn(mappedBoard);
        when(mapper.mapToBoardDto(anyList())).thenReturn(new ArrayList<>());
        when(validator.validateTrelloBoards(mappedBoard)).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> board = facade.fetchTrelloBoards();

        //Than
        assertNotNull(board);
        assertEquals(0,board.size());
    }

    @Test
    public void shouldFetchTrelloBoards(){
        //Given
        List<TrelloListDto> dtoList = new ArrayList<>();
        dtoList.add(new TrelloListDto("1","list one",true));

        List<TrelloBoardDto> boardDtoList = new ArrayList<>();
        boardDtoList.add(new TrelloBoardDto("1","item one",dtoList));

        List<TrelloList> mappedList = new ArrayList<>();
        mappedList.add(new TrelloList("1","list one",true));

        List<TrelloBoard> mappedBoard = new ArrayList<>();
        mappedBoard.add(new TrelloBoard("1","item one",mappedList));

        when(service.fetchTrelloBoards()).thenReturn(boardDtoList);
        when(mapper.mapToTrelloBoard(boardDtoList)).thenReturn(mappedBoard);
        when(mapper.mapToBoardDto(anyList())).thenReturn(boardDtoList);
        when(validator.validateTrelloBoards(mappedBoard)).thenReturn(mappedBoard);

        //When
        List<TrelloBoardDto> boards = facade.fetchTrelloBoards();

        //Then
        assertNotNull(boards);
        assertEquals(1,boards.size());

        boards.forEach(board -> {assertEquals("1",board.getId());
        assertEquals("item one",board.getName());
        board.getLists().forEach(list-> {assertEquals("1",list.getId());
        assertEquals("list one",list.getName());
        assertEquals(true,list.isClosed());
        });
        });
    }

}