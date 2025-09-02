package com.minimax.minimax.connect4.infrastructure.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minimax.minimax.connect4.domain.CellPair;
import com.minimax.minimax.connect4.domain.Player;
import com.minimax.minimax.connect4.domain.Table;
import com.minimax.minimax.connect4.service.Connect4Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ResponseMapperTest {
    Connect4Game game;
    ResponseMapper requestMapper;

    Table table2x2;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        table2x2 = new Table(2, 2);
        game = mock(Connect4Game.class);
        when(game.getTable()).thenReturn(table2x2);
        when(game.getCellsOfConnect4()).thenReturn(
                List.of(
                        new CellPair(table2x2.getCell(0,0), table2x2.getCell(0,1)),
                        new CellPair(table2x2.getCell(1,0), table2x2.getCell(1,1))));

        requestMapper = new ResponseMapper();
    }

    @Test
    void empty_table() {
       when(game.getTable()).thenReturn(new Table(2,2));

       var sut = requestMapper.mapConnect4Game(game);

       assertEquals("[[], []]", sut.getTable().toString());
    }

    @Test
    void table_with_one_row() {
        table2x2.placePlayer1AtColumn(0);
        table2x2.placePlayer2AtColumn(0);
        table2x2.placePlayer1AtColumn(1);

        var sut = requestMapper.mapConnect4Game(game);

        assertEquals("[[1, 2], [1]]", sut.getTable().toString());
    }

    @Test
    void getCellsOfConnect4_and_player_1_winner(){
        when(game.getStatus()).thenReturn(Connect4Game.STATUS.WINNER);
        when(game.getWinner()).thenReturn(Player.PLAYER_1);

        var sut = requestMapper.mapConnect4Game(game);

        assertEquals("[[[0, 0], [0, 1]], [[1, 0], [1, 1]]]",
                sut.getCellsOfConnect4().toString());
        assertEquals(Player.PLAYER_1, sut.getWinner());
        verify(game).getCellsOfConnect4();
    }

    @Test
    void getCellsOfConnect4_and_winner_and_player_2_winner(){
        when(game.getStatus()).thenReturn(Connect4Game.STATUS.WINNER);
        when(game.getWinner()).thenReturn(Player.PLAYER_2);

        var sut = requestMapper.mapConnect4Game(game);

        assertEquals(Player.PLAYER_2, sut.getWinner());
        verify(game).getCellsOfConnect4();
    }

    @Test
    void getStatus_playing(){
        when(game.getStatus()).thenReturn(Connect4Game.STATUS.PLAYING);

        var sut = requestMapper.mapConnect4Game(game);

        assertEquals(Connect4Game.STATUS.PLAYING, sut.getStatus());
        verify(game, never()).getCellsOfConnect4();
    }

    /*
     *  ObjectMapper map to json
     */
    @Test
    void mapToJson_gameStatusPLAYING() throws Exception {
        table2x2.placePlayer1AtColumn(1);
        when(game.getStatus()).thenReturn(Connect4Game.STATUS.PLAYING);
        var response = requestMapper.mapConnect4Game(game);

        var sut = objectMapper.writeValueAsString(response);

        assertEquals("{\"table\":[[],[1]]," +
                "\"status\":\"PLAYING\"}", sut);
    }

    @Test
    void mapToJson_gameStatusWINNER() throws Exception {
        when(game.getStatus()).thenReturn(Connect4Game.STATUS.WINNER);
        when(game.getWinner()).thenReturn(Player.PLAYER_2);
        var response = requestMapper.mapConnect4Game(game);

        var sut = objectMapper.writeValueAsString(response);

        assertEquals("{\"table\":[[],[]]," +
                "\"status\":\"WINNER\"," +
                "\"winner\":\"PLAYER_2\"," +
                "\"cells_of_connect4\":[[[0,0],[0,1]],[[1,0],[1,1]]]}", sut);
    }

}
