package com.minimax.minimax.infrastructure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RestController
@RequestMapping("/isHealthy")
public class apiController {

    @GetMapping
    public String health() {
        return "Is healthy " + getVersion();
    }

    private String getVersion() {
        try (InputStream stream = getClass().getResourceAsStream(
                "/META-INF/maven/com.minimax/minimax/pom.properties")) {
            if (stream != null) {
                Properties properties = new Properties();
                properties.load(stream);
                return properties.getProperty("version", "unknown");
            }
        } catch (IOException e) {
            // ignore and return unknown
        }
        return "unknown";
    }

}


