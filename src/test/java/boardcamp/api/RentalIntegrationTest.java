package boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

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

import boardcamp.api.dtos.RentalDTO;
import boardcamp.api.models.CustomerModel;
import boardcamp.api.models.GameModel;
import boardcamp.api.models.RentalModel;
import boardcamp.api.repositories.CustomerRepository;
import boardcamp.api.repositories.GameRepository;
import boardcamp.api.repositories.RentalRepository;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RentalIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    void cleanUpDb(){
        rentalRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    void integration_givenValidRental_whenCreatingRental_thenCreatesRental() {
        CustomerModel customer = customerRepository.save(new CustomerModel(null, "User", "21999999999", "12345678910"));
        GameModel game = gameRepository.save(new GameModel(null, "War", "img", 10, 1500));

        RentalDTO rental = new RentalDTO(customer.getId(), game.getId(), 3);
        HttpEntity<RentalDTO> body = new HttpEntity<>(rental);

        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals",
            HttpMethod.POST,
            body,
            String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, rentalRepository.count());
    }

    @Test
    void integration_givenActiveRental_whenReturning_thenSetsReturnDate(){
        CustomerModel customer = customerRepository.save(new CustomerModel(null, "User", "21987654321", "12345678910"));
        GameModel game = gameRepository.save(new GameModel(null, "War", "img", 10, 1500));
        RentalModel rental = rentalRepository.save(new RentalModel(LocalDate.now().minusDays(5), 3, 300, customer, game));

        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals/" + rental.getId() + "/return", 
            HttpMethod.POST, 
            null, 
            String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(rentalRepository.findById(rental.getId()).get().getReturnDate());
    }

    @Test
    void integration_givenReturnedRental_whenDeleting_thenDeletesRental() {
        CustomerModel customer = customerRepository.save(new CustomerModel(null, "User", "21999999999", "12345678910"));
        GameModel game = gameRepository.save(new GameModel(null, "War", "img", 10, 1500));
        RentalModel rental = new RentalModel(LocalDate.now().minusDays(5), 3, 300, customer, game);
        rental.setReturnDate(LocalDate.now());
        rental = rentalRepository.save(rental);

        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals/" + rental.getId(),
            HttpMethod.DELETE, 
            null, 
            String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, rentalRepository.count());
    }

    @Test
    void integration_givenActiveRental_whenDeleting_thenThrowsError() {
        CustomerModel customer = customerRepository.save(new CustomerModel(null, "User", "21999999999", "12345678910"));
        GameModel game = gameRepository.save(new GameModel(null, "War", "img", 10, 1500));
        RentalModel rental = new RentalModel(LocalDate.now(), 3, 300, customer, game);
        rentalRepository.save(rental);

        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals/" + rental.getId(), 
            HttpMethod.DELETE, 
            null, 
            String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, rentalRepository.count());
    }
}
