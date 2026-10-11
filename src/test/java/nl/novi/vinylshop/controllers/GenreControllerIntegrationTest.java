package nl.novi.vinylshop.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GenreControllerIntegrationTest{

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("POST Pop Genre test - geeft 201")
    void createGenre_returns201_whenValidGenre() throws Exception {
        // Arrange: de JSON-body, net als in Postman
        String requestJson = """
            {
                "name": "Pop",
                "description": "Popmuziek is een toegankelijk genre gemaakt voor een breed publiek. Het kenmerkt zich door een herkenbare opbouw met een catchy refrein en makkelijke melodieën. De teksten gaan over herkenbare emoties, verpakt in een moderne, gladde productie"
            }
            """;

        // Act + Assert
        mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pop"));
    }

    @Test
    @DisplayName("POST Jazz Genre test - geeft 400")
    void createGenre_returns400_whenDescriptionTooLong() throws Exception {
        // Arrange: de JSON-body, net als in Postman
        String requestJson = """
            {
                "name": "Jazz",
                "description": "Jazz ontstond begin twintigste eeuw in New Orleans en draait om improvisatie, swing en complexe harmonieën. Muzikanten reageren live op elkaar, waardoor geen uitvoering hetzelfde klinkt. Saxofoon, trompet, piano en contrabas spelen vaak de hoofdrol in dit veelzijdige genre."
            }
            """;

        // Act + Assert
        mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").value("Beschrijving mag niet langer zijn dan 255 karakters"));
    }

}
