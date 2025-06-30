package boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import boardcamp.api.dtos.GameDTO;
import boardcamp.api.errors.GameNameConflict;
import boardcamp.api.errors.GameNotFound;
import boardcamp.api.models.GameModel;
import boardcamp.api.repositories.GameRepository;

@Service
public class GameService {
    final GameRepository gameRepository;

    GameService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    public List<GameModel> getGames(){
        return gameRepository.findAll();
    }

    public Optional<GameModel> getGameId(Long id){
        Optional<GameModel> game = gameRepository.findById(id);

        if(!game.isPresent()){
            throw new GameNotFound("Jogo com esse id não encontrado");
        }else{
            return game;
        }
    }

    public Optional<GameModel> postGame(GameDTO body){
        if(gameRepository.existsByName(body.getName())){
            throw new GameNameConflict("Jogo com esse nome já cadastrado");
        }

        GameModel game = new GameModel(body);
        gameRepository.save(game);
        return Optional.of(game);
    }
}
