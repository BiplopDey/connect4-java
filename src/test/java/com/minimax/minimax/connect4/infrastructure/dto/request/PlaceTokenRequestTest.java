package com.minimax.minimax.connect4.infrastructure.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PlaceTokenRequestTest {
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test() throws JsonProcessingException {

        var table2x2 = List.of(new ArrayList<Integer>(), List.of(2,1));
        var request = new PlaceTokenRequest(table2x2, 1, 1, 2);

        var sut = objectMapper.writeValueAsString(request);

        assertEquals("{\"table\":[[],[2,1]],\"player\":1,\"column\":1,\"row_size\":2}",
                sut);
    }
}
