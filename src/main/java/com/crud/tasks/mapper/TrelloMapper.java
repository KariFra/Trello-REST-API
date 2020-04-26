package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloMapper {

    public List<TrelloBoard> mapToTrelloBoard(final List<TrelloBoardDto> trelloBoardDto){
        return trelloBoardDto.stream().map(board -> new TrelloBoard(board.getId(),board.getName(),mapToTrelloList(board.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloBoardDto> mapToBoardDto(final List<TrelloBoard> trelloBoard){
        return trelloBoard.stream().map(board -> new TrelloBoardDto(board.getId(),board.getName(),mapToListDto(board.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloList> mapToTrelloList(final List<TrelloListDto> trelloListDto){
        return trelloListDto.stream().map(list -> new TrelloList(list.getId(),list.getName(),list.isClosed()))
                .collect(Collectors.toList());
    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloList){
        return trelloList.stream().map(list -> new TrelloListDto(list.getId(),list.getName(),list.isClosed()))
                .collect(Collectors.toList());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto){
        return new TrelloCard(trelloCardDto.getName(),trelloCardDto.getDescription(),trelloCardDto.getPos(),trelloCardDto.getListId());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard){
        return new TrelloCardDto(trelloCard.getName(),trelloCard.getDescription(),trelloCard.getPos(),trelloCard.getListId());
    }

}
