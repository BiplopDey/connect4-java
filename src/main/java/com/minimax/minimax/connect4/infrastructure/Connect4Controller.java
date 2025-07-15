package com.minimax.minimax.connect4.infrastructure;

import com.minimax.minimax.connect4.infrastructure.dto.request.PlaceTokenRequest;
import com.minimax.minimax.connect4.infrastructure.dto.request.RequestMapper;
import com.minimax.minimax.connect4.infrastructure.dto.response.Connect4GameResponse;
import com.minimax.minimax.connect4.infrastructure.dto.response.ResponseMapper;
import com.minimax.minimax.connect4.service.Connect4Game;
import com.minimax.minimax.connect4.service.Game;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connect4")
public class Connect4Controller {

    @CrossOrigin
    @PostMapping("/placeToken")
    public Connect4GameResponse placeToken(@RequestBody PlaceTokenRequest request) {
        try {
            var mapper = new RequestMapper();
            Connect4Game game = new Game(mapper.mapToConnect4Table(request));
            game.place(mapper.getPlayer(request.player), request.column);
            return new ResponseMapper().mapConnect4Game(game);
        } catch (Exception e) {
            throw new BadArgumentsException(e.getMessage());
        }
    }

}
