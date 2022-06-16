package com.minimax.minimax.connect4.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minimax.minimax.connect4.domain.ColumnFullException;
import com.minimax.minimax.connect4.service.Connect4Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Connect4Controller.class)
class Connect4ControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(Connect4ControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Connect4Game game;

    @Autowired
    private ObjectMapper ObjectMapper;

    @Test
    void hello() throws Exception {
        mockMvc.perform(get("/connect4/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }

    @Test
    void placePlayer1AtColumn2() throws Exception {
        String requestBody = "{\"table\":[[0,0],[2,1]],\"player\":1,\"column\":2}";

        var sut = mockMvcPlaceToken(requestBody)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.table", is("[[0, 0], [2, 1]]")));

        logger.info("response: " + getResponseAsString(sut));
    }

    @Test
    void invalidTableRowSize_requestBody_throws_BadArgumentsException() throws Exception {
        String requestBody = "{\"table\":[[0,0,0],[2,1]],\"player\":1,\"column\":1}";
        assertBadPlaceTokenRequest(requestBody,"Invalid table row size");
    }

    @Test
    void invalidTableColumnPositions_requestBody_throws_BadArgumentsException() throws Exception {
        String requestBody = "{\"table\":[[2,0],[0,1],[1,1]],\"player\":1,\"column\":1}";
        assertBadPlaceTokenRequest(requestBody,"Invalid table columns position");
    }

    @Test
    void invalidColumn_requestBody_throws_BadArgumentsException() throws Exception {
        String requestBody = "{\"table\":[[0,0],[2,1],[1,1]],\"player\":1,\"column\":0}";
        assertBadPlaceTokenRequest(requestBody,"Invalid column");
    }

    @Test
    void invalidPlayer_requestBody_throws_ColumnFullException() throws Exception {
        String requestBody = "{\"table\":[[0,0],[2,1]],\"player\":0,\"column\":1}";
        assertBadPlaceTokenRequest(requestBody, "Invalid player");
    }

    @Test
    void invalidTableContent_requestBody_throws_ColumnFullException() throws Exception {
        String requestBody = "{\"table\":[[3,0],[2,1]],\"player\":0,\"column\":1}";
        assertBadPlaceTokenRequest(requestBody, "Invalid table content");
    }

    @Test
    void columnFull_requestBody_throws_ColumnFullException() throws Exception {
        when(game.placePlayer1AtColumn(1))
                .thenThrow(new ColumnFullException("Column 1 full"));

        String requestBody = "{\"table\":[[0,1],[2,1]],\"player\":1,\"column\":1}";
        assertBadPlaceTokenRequest(requestBody,  "Column 1 full");
    }

    private void assertBadPlaceTokenRequest(String requestBody, String expectedExceptionMessage) throws Exception {
        mockMvcPlaceToken(requestBody)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof BadArgumentsException))
                .andExpect(result ->
                        assertEquals(expectedExceptionMessage, result.getResolvedException().getMessage()));
    }

    private ResultActions mockMvcPlaceToken(String requestBody) throws Exception {
        return mockMvc.perform(post("/connect4/placeToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody));
    }

    private String getResponseAsString(ResultActions resultActions) throws Exception {
        return resultActions.andReturn().getResponse().getContentAsString();
    }
}