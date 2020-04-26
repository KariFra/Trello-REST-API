package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
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
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;


    @Test
    public void testMapBoard(){
        //Given
        TrelloList listOne = new TrelloList("1","one",true);
        TrelloList listTwo = new TrelloList("2","two",true);
        ArrayList<TrelloList> list = new ArrayList();
        list.add(listOne);
        list.add(listTwo);

        TrelloBoard boardOne = new TrelloBoard("1","First board",list);
        TrelloBoard boardTwo = new TrelloBoard("2","Second board",list);
        ArrayList<TrelloBoard> boardList = new ArrayList<>();
        boardList.add(boardOne);
        boardList.add(boardTwo);

        //When
        List<TrelloBoardDto> trelloBoardDto = trelloMapper.mapToBoardDto(boardList);
        List<TrelloBoard> trelloBoards = trelloMapper.mapToTrelloBoard(trelloBoardDto);

        //Than
        assertTrue(trelloBoardDto.size()==2);
        assertTrue(trelloBoards.size()==2);
    }

    @Test
    public void testMapList(){
        //Given
        TrelloList listOne = new TrelloList("1","one",true);
        TrelloList listTwo = new TrelloList("2","two",true);
        ArrayList<TrelloList> list = new ArrayList();
        list.add(listOne);
        list.add(listTwo);

        //When
        List<TrelloListDto> listDtos = trelloMapper.mapToListDto(list);
        List<TrelloList> lists = trelloMapper.mapToTrelloList(listDtos);

        //Than
        assertTrue(listDtos.size()==2);
        assertTrue(lists.size()==2);
    }

    @Test
    public void testMapCard(){
        //Given
        TrelloCard newCard = new TrelloCard("card 1","first card","1","1");

        //When
        TrelloCardDto cardDto = trelloMapper.mapToCardDto(newCard);
        TrelloCard card = trelloMapper.mapToCard(cardDto);

        //Than
        assertTrue(cardDto.getPos()=="1");
        assertTrue(card.getPos()=="1");
    }


}