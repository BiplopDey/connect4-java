package com.minimax.minimax.infrastructure;

import com.minimax.minimax.connect4.infrastructure.Connect4Controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(apiController.class)
class apiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void health() throws Exception {
        String version = getVersion();
        mockMvc.perform(get("/isHealthy"))
                .andExpect(status().isOk())
                .andExpect(content().string("Is healthy " + version));
    }

    private String getVersion() throws IOException {
        try (InputStream stream = getClass().getResourceAsStream("/version.properties")) {
            if (stream != null) {
                Properties properties = new Properties();
                properties.load(stream);
                return properties.getProperty("version", "unknown");
            }
        }
        return "unknown";
    }

}

