package boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import boardcamp.api.dtos.GameDTO;
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
            return Optional.empty();
        }else{
            return game;
        }
    }

    public Optional<GameModel> postGame(GameDTO body){
        if(gameRepository.existsByName(body.getName())){
            return Optional.empty();
        }

        GameModel game = new GameModel(body);
        gameRepository.save(game);
        return Optional.of(game);
    }
}
