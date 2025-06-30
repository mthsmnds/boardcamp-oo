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

import boardcamp.api.dtos.CustomerDTO;
import boardcamp.api.errors.CustomerCpfConflict;
import boardcamp.api.errors.CustomerNotFound;
import boardcamp.api.models.CustomerModel;
import boardcamp.api.repositories.CustomerRepository;
import boardcamp.api.services.CustomerService;

@SpringBootTest
class CustomerUnitTests {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Test
	void givenRepeatedCpf_whenCreatingCustomer_thenThrowsError() {
		// given
		CustomerDTO customer = new CustomerDTO("Test", "Test", "Test");
		doReturn(true).when(customerRepository).existsByCpf(any());

		// when
		CustomerCpfConflict error = assertThrows(CustomerCpfConflict.class,
				() -> customerService.postCustomer(customer));

		// then
		verify(customerRepository, times(1)).existsByCpf(any());
		verify(customerRepository, times(0)).save(any());
		assertNotNull(error);
		assertEquals("Um usuário com esse cpf já está cadastrado", error.getMessage());

	}

	@Test
	void givenInvalidId_whenGettingCustomer_thenThrowsError() {
		// given
		doReturn(Optional.empty()).when(customerRepository).findById(any());

		// when
		CustomerNotFound error = assertThrows(CustomerNotFound.class,
				() -> customerService.getCustomerId(any()));

		// then
		verify(customerRepository, times(1)).findById(any());
		assertNotNull(error);
		assertEquals("Usuário com esse id não encontrado", error.getMessage());

	}

	@Test
	void givenValidCpf_whenCreatingCustomer_thenCreatesCustomer() {
		// given
		CustomerDTO customer = new CustomerDTO("Test", "Test", "Test");
		CustomerModel newCustomer = new CustomerModel(customer);

		doReturn(false).when(customerRepository).existsByCpf(any());
		doReturn(newCustomer).when(customerRepository).save(any());

		// when
		CustomerModel result = customerService.postCustomer(customer);

		// then
		verify(customerRepository, times(1)).existsByCpf(any());
		verify(customerRepository, times(1)).save(any());
		assertEquals(newCustomer, result);
	}

}
