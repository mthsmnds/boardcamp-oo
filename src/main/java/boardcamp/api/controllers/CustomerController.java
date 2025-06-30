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

import boardcamp.api.dtos.CustomerDTO;
import boardcamp.api.models.CustomerModel;
import boardcamp.api.services.CustomerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    final CustomerService customerService;

    CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping()
        public ResponseEntity<Object> getCustomers(){
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers());
        }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerId(@PathVariable("id") Long id){
        Optional<CustomerModel> customer = customerService.getCustomerId(id);

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PostMapping()
    public ResponseEntity<Object> postCustomer(@RequestBody @Valid CustomerDTO body){
        CustomerModel customer = customerService.postCustomer(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
    
}
