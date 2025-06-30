package boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import boardcamp.api.dtos.GameDTO;
import boardcamp.api.errors.GameNameConflict;
import boardcamp.api.errors.GameNotFound;
import boardcamp.api.models.GameModel;
import boardcamp.api.repositories.GameRepository;
import boardcamp.api.services.GameService;

@SpringBootTest
class GameUnitTests {
    
	@InjectMocks
	private GameService gameService;

	@Mock
	private GameRepository gameRepository;

	@Test
	void givenRepeatedName_whenCreatingGame_thenThrowsError() {
		//given
		GameDTO game = new GameDTO("Test", 1, 1);

		doReturn(true).when(gameRepository).existsByName(any());

		//when
		GameNameConflict error = assertThrows(GameNameConflict.class,
		 ()-> gameService.postGame(game));

		//then
		verify(gameRepository, times(1)).existsByName(any());
		verify(gameRepository, times(0)).save(any());
		assertNotNull(error);
		assertEquals("Jogo com esse nome já cadastrado", error.getMessage());

	}

	@Test
	void givenInvalidId_whenGettingCustomer_thenThrowsError() {
		//given
		doReturn(Optional.empty()).when(gameRepository).findById(any());

		//when
		GameNotFound error = assertThrows(GameNotFound.class,
		 ()-> gameService.getGameId(any()));

		//then
		verify(gameRepository, times(1)).findById(any());
		assertNotNull(error);
		assertEquals("Jogo com esse id não encontrado", error.getMessage());

	}

	@Test
	void givenValidName_whenCreatingCustomer_thenCreatesCustomer() {
		//given
		GameDTO game = new GameDTO("Test", 1, 1);
		GameModel newGame = new GameModel(game);

		doReturn(false).when(gameRepository).existsByName(any());
		doReturn(newGame).when(gameRepository).save(any());

		//when
		GameModel result = gameService.postGame(game);

		//then
		verify(gameRepository, times(1)).existsByName(any());
		verify(gameRepository, times(1)).save(any());
		assertEquals(newGame, result);
	}

}
