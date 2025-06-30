package boardcamp.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import boardcamp.api.dtos.RentalDTO;
import boardcamp.api.errors.CustomerNotFound;
import boardcamp.api.errors.GameNotAvaiable;
import boardcamp.api.errors.GameNotFound;
import boardcamp.api.models.CustomerModel;
import boardcamp.api.models.GameModel;
import boardcamp.api.models.RentalModel;
import boardcamp.api.repositories.CustomerRepository;
import boardcamp.api.repositories.GameRepository;
import boardcamp.api.repositories.RentalRepository;
import boardcamp.api.services.RentalService;

@SpringBootTest
class RentalUnitTests {

    @InjectMocks
    private RentalService rentalService;

    @Mock
    private GameRepository gameRepository;


    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void givenNonexistentCustomer_whenCreatingRental_thenThrowsError() {
        //given
        RentalDTO rental = new RentalDTO(1L, 1L, 3);
        doReturn(Optional.empty()).when(customerRepository).findById(1L);

        //when 
        CustomerNotFound error = assertThrows(CustomerNotFound.class,
        () -> rentalService.postRental(rental));

        //then
        verify(customerRepository, times(1)).findById(any());
        verify(gameRepository, times(0)).findById(any());
        verify(rentalRepository, times(0)).activeRentals(any());
        verify(rentalRepository, times(0)).save(any());
        assertNotNull(error);
        assertEquals("Cliente não encontrado", error.getMessage());
    }

    @Test
    void givenOutOfStockGame_whenCreatingRental_thenThrowsError() {
        //given
        RentalDTO rental = new RentalDTO(1L, 1L, 3);
        CustomerModel customer = new CustomerModel();
        customer.setId(1L);
        GameModel game = new GameModel();
        game.setId(1L);
        game.setPricePerDay(1500);
        game.setStockTotal(2);

        doReturn(Optional.of(customer)).when(customerRepository).findById(1L);
        doReturn(Optional.of(game)).when(gameRepository).findById(1L);
        doReturn(2).when(rentalRepository).activeRentals(1L);

        //when
        GameNotAvaiable error = assertThrows(GameNotAvaiable.class,
        () -> rentalService.postRental(rental));

        //then
        verify(customerRepository, times(1)).findById(any());
        verify(gameRepository, times(1)).findById(any());
        verify(rentalRepository, times(1)).activeRentals(any());
        verify(rentalRepository, times(0)).save(any());
        assertNotNull(error);
        assertEquals("Jogo indisponível no estoque", error.getMessage());

    }

    @Test
    void givenInvalidGameId_whenCreatingRental_thenThrowsError() {
        //given
        RentalDTO rental = new RentalDTO(1L, 1L, 3);
        CustomerModel customer = new CustomerModel();
        customer.setId(1L);

        doReturn(Optional.of(customer)).when(customerRepository).findById(1L);
        doReturn(Optional.empty()).when(gameRepository).findById(1L);

        // when
        GameNotFound error = assertThrows(GameNotFound.class, 
        () -> rentalService.postRental(rental));

        //then
        verify(customerRepository, times(1)).findById(any());
        verify(gameRepository, times(1)).findById(any());
        verify(rentalRepository, times(0)).activeRentals(any());
        verify(rentalRepository, times(0)).save(any());
        assertNotNull(error);
        assertEquals("Jogo não encontrado", error.getMessage());
    }

    @Test
    void givenValidRental_whenCreatingRental_thenCreatesRental() {
        //given
        RentalDTO rental = new RentalDTO(1L, 1L, 3);
        CustomerModel customer = new CustomerModel();
        GameModel game = new GameModel();
        game.setPricePerDay(1500);
        game.setStockTotal(5);

        RentalModel newRental = new RentalModel(LocalDate.now(), 3, 4500, customer, game);

        doReturn(Optional.of(customer)).when(customerRepository).findById(1L);
        doReturn(Optional.of(game)).when(gameRepository).findById(1L);
        doReturn(2).when(rentalRepository).activeRentals(1L);
        doReturn(rental).when(rentalRepository).save(any());

        //when
        RentalModel result = rentalService.postRental(rental);

        //then
        verify(customerRepository, times(1)).findById(1L);
        verify(gameRepository, times(1)).findById(1L);
        verify(rentalRepository, times(1)).activeRentals(1L);
        verify(rentalRepository, times(1)).save(any());
        assertEquals(newRental, result);
    }
}
