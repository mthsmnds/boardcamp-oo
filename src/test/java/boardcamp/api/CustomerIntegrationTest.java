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

import boardcamp.api.dtos.CustomerDTO;
import boardcamp.api.models.CustomerModel;
import boardcamp.api.repositories.CustomerRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CustomerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;
    
    @BeforeEach
    @AfterEach
    void cleanUpDb(){
        customerRepository.deleteAll();
    }
    
    @Test
    void integration_givenRepeatedCpf_whenCreatingCustomer_thenThrowsError(){
        CustomerModel customer = new CustomerModel(null, "Johnny Test", "21987654321", "12345678910");
        customerRepository.save(customer);

        CustomerDTO conflictCustomer = new CustomerDTO("Johnny Quest", "22987654321", "12345678910");
        HttpEntity<CustomerDTO> body = new HttpEntity<>(conflictCustomer);

        ResponseEntity<String> response = restTemplate.exchange(
            "/customers",
            HttpMethod.POST, 
            body, 
            String.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(1, customerRepository.count());
    }

    @Test
    void integration_givenValidCustomer_whenCreatingCustomer_thenCreatesCustomer(){
        CustomerDTO customer = new CustomerDTO("Test User", "21999999999", "00000000000");
        HttpEntity<CustomerDTO> body = new HttpEntity<>(customer);

        ResponseEntity<String> response = restTemplate.exchange("/customers", HttpMethod.POST, body, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, customerRepository.count());
    }
}
