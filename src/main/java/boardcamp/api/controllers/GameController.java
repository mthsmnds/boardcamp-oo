package boardcamp.api.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boardcamp.api.dtos.GameDTO;
import boardcamp.api.models.GameModel;
import boardcamp.api.services.GameService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/games")
public class GameController {
    final GameService gameService;

    GameController(GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping()
    public ResponseEntity<Object> getGames(){
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGameId(@PathVariable("id") Long id){
        Optional<GameModel> game = gameService.getGameId(id);

        if(!game.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo com esse ID não encontrado");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(game.get());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> postGame(@RequestBody @Valid GameDTO body){
        GameModel game = gameService.postGame(body);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
}
