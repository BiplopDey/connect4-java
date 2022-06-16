package com.minimax.minimax.connect4.infrastructure;

import com.minimax.minimax.connect4.infrastructure.dto.request.PlaceTokenRequest;
import com.minimax.minimax.connect4.infrastructure.dto.request.RequestMapper;
import com.minimax.minimax.connect4.service.Connect4Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/connect4")
public class Connect4Controller {
    private final Connect4Game game;
    @Autowired
    public Connect4Controller(Connect4Game game) {
        this.game = game;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/placeToken")
    public Map<String, String> placeToken(@RequestBody PlaceTokenRequest request) {
        try{
            new RequestMapper().mapToConnect4Table(request);
            game.placePlayer1AtColumn(request.column);
            Map<String, String> result = Map.of("table", request.table.toString());
            return result;
        } catch (Exception e) {
            throw new BadArgumentsException(e.getMessage());
        }
    }

}
