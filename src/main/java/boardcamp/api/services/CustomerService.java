package boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import boardcamp.api.dtos.CustomerDTO;
import boardcamp.api.errors.CustomerCpfConflict;
import boardcamp.api.errors.CustomerNotFound;
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
            throw new CustomerNotFound("Usuário com esse id não encontrado");
        }else{
            return customer;
        }
    }

    public CustomerModel postCustomer(CustomerDTO body){
        if(customerRepository.existsByCpf(body.getCpf())){
            throw new CustomerCpfConflict("Um usuário com esse cpf já está cadastrado");
        }

        CustomerModel customer = new CustomerModel(body);
        return customerRepository.save(customer);
        
    }
    
}
