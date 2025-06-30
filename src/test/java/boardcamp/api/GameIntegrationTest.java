package boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import boardcamp.api.dtos.GameDTO;
import boardcamp.api.models.GameModel;
import boardcamp.api.repositories.GameRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GameIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    void cleanUpDb(){
        gameRepository.deleteAll();
    }

    @Test
    void integration_givenRepeatedGameName_whenCreatingGame_thenThrowsError(){
        GameModel game = new GameModel(null, "Game", "image", 5, 1500); 
        gameRepository.save(game);

        GameDTO conflictGame = new GameDTO("Game", 5, 1500);
        HttpEntity<GameDTO> body = new HttpEntity<>(conflictGame);

        ResponseEntity<String> response = restTemplate.exchange(
            "/games", 
            HttpMethod.POST, 
            body, 
            String.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(1, gameRepository.count());
    }

    @Test
    void integration_givenValidGame_whenCreatingGame_thenCreatesGame(){
        GameDTO game = new GameDTO("Game", 5, 1500);
        HttpEntity<GameDTO> body = new HttpEntity<>(game);

        ResponseEntity<String> response = restTemplate.exchange(
            "/games", 
            HttpMethod.POST, 
            body, 
            String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, gameRepository.count());
    }
}
