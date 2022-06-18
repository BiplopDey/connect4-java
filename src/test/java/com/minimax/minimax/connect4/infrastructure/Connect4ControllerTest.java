package com.minimax.minimax.connect4.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Connect4Controller.class)
class Connect4ControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(Connect4ControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void hello() throws Exception {
        mockMvc.perform(get("/connect4/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }

    @Test
    void invalid_player_and_throws_ColumnFullException() throws Exception {
        String requestBody = "{\"table\":[[1],[2,1]],\"player\":0,\"column\":1,\"row_size\":2}";
        assertBadPlaceTokenRequest(requestBody, "Invalid player");
    }

    @Test
    void invalid_cell_value_and_throws_ColumnFullException() throws Exception {
        String requestBody = "{\"table\":[[0,0],[2,1]],\"player\":1,\"column\":1,\"row_size\":2}";
        assertBadPlaceTokenRequest(requestBody, "Invalid cell value");
    }

    @Test
    void invalid_rowSize_throws_BadArgumentsException() throws Exception {
        String requestBody = "{\"table\":[[1],[2,1]],\"player\":1,\"column\":1,\"row_size\":1}";
        assertBadPlaceTokenRequest(requestBody,
                "All columns length should be less than row size specified");
    }

    @Test
    void columnFull_throws_ColumnFullException() throws Exception {
        String requestBody = "{\"table\":[[1],[2,1]],\"player\":1,\"column\":2,\"row_size\":2}";
        assertBadPlaceTokenRequest(requestBody,  "Column 1 is full");
    }

    @Test
    void place_player1_at_column_2_and_status_is_playing() throws Exception {
        String requestBody = "{\"table\":[[1],[2]],\"player\":1,\"column\":2,\"row_size\":2}";

        var sut = mockMvcPlaceToken(requestBody)
                .andExpect(status().isOk());

        String response = getResponseAsString(sut);
        logger.info("response: {}", getResponseAsString(sut));
        JSONAssert.assertEquals(
                "{\"table\":[[1],[2,1]],\"status\":\"PLAYING\"}",
                response, JSONCompareMode.STRICT);
    }

    @Test
    void place_player2_at_column_1_and_status_is_draw() throws Exception {
        String requestBody = "{\"table\":[[1],[2,1]],\"player\":2,\"column\":1,\"row_size\":2}";

        var sut = mockMvcPlaceToken(requestBody)
                .andExpect(status().isOk());

        String response = getResponseAsString(sut);
        logger.info("response: {}", getResponseAsString(sut));
        JSONAssert.assertEquals(
                "{\"table\":[[1,2],[2,1]],\"status\":\"DRAW\"}",
                response, JSONCompareMode.STRICT);
    }

    @Test
    void place_player1_and_status_is_winner_connect4_in_column() throws Exception {
        String requestBody = "{\"table\":[[1,1,1],[2,1]],\"player\":1,\"column\":1,\"row_size\":4}";

        var sut = mockMvcPlaceToken(requestBody)
                .andExpect(status().isOk());

        String response = getResponseAsString(sut);
        logger.info("response: {}", getResponseAsString(sut));
        JSONAssert.assertEquals("{\"table\":[[1,1,1,1],[2,1]]," +
                        "\"status\":\"WINNER\"," +
                        "\"winner\":\"PLAYER_1\"," +
                        "\"cells_of_connect4\":[[[0,0],[3,0]]]}",
                response, JSONCompareMode.STRICT);
    }

    @Test
    void place_player2_and_status_is_winner_connect4_in_row() throws Exception {
        String requestBody = "{\"table\":[[2,1],[2,1],[],[2]],\"player\":2,\"column\":3,\"row_size\":4}";

        var sut = mockMvcPlaceToken(requestBody)
                .andExpect(status().isOk());

        String response = getResponseAsString(sut);
        logger.info("response: {}", getResponseAsString(sut));
        JSONAssert.assertEquals("{\"table\":[[2,1],[2,1],[2],[2]]," +
                        "\"status\":\"WINNER\"," +
                        "\"winner\":\"PLAYER_2\"," +
                        "\"cells_of_connect4\":[[[0,0],[0,3]]]}",
                response, JSONCompareMode.STRICT);
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