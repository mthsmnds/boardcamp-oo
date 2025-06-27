package boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import boardcamp.api.dtos.CustomerDTO;
import boardcamp.api.models.CustomerModel;
import boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<CustomerModel> getCustomers(){
        return customerRepository.findAll();
    }

    public Optional<CustomerModel> getCustomerId(Long id){
        Optional<CustomerModel> customer = customerRepository.findById(id);

        if(!customer.isPresent()){
            return Optional.empty();
        }else{
            return customer;
        }
    }

    public Optional<CustomerModel> postCustomer(CustomerDTO body){
        if(customerRepository.existsByCpf(body.getCpf())){
            return Optional.empty();
        }

        CustomerModel customer = new CustomerModel(body);
        customerRepository.save(customer);
        return Optional.of(customer);
    }
    
}
