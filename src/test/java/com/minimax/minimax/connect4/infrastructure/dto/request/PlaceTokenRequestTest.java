package com.minimax.minimax.connect4.infrastructure.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceTokenRequestTest {

    @Test
    void test() throws JsonProcessingException {
        var table2x2 = List.of(List.of(0,0), List.of(2,1));
        var request = new PlaceTokenRequest(table2x2, 1, 1);

        assertEquals("{\"table\":[[0,0],[2,1]],\"player\":1,\"column\":1}",
                new ObjectMapper().writeValueAsString(request));
    }
}
