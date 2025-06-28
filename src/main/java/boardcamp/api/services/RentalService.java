package boardcamp.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import boardcamp.api.dtos.RentalDTO;
import boardcamp.api.models.CustomerModel;
import boardcamp.api.models.GameModel;
import boardcamp.api.models.RentalModel;
import boardcamp.api.repositories.CustomerRepository;
import boardcamp.api.repositories.GameRepository;
import boardcamp.api.repositories.RentalRepository;

@Service
public class RentalService {
    final RentalRepository rentalRepository;
    final GameRepository gameRepository;
    final CustomerRepository customerRepository;

    RentalService(RentalRepository rentalRepository, GameRepository gameRepository, CustomerRepository customerRepository){
        this.rentalRepository = rentalRepository;
        this.gameRepository = gameRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<RentalModel> postRental(RentalDTO dto){
        Optional<CustomerModel> customer = customerRepository.findById(dto.getCustomerId());
        Optional<GameModel> game = gameRepository.findById(dto.getGameId());
        
        if(customer.isEmpty() || game.isEmpty()){
            return Optional.empty();
        }

        int pricePerDay = game.get().getPricePerDay();
        int originalPrice = pricePerDay * dto.getDaysRented();

        RentalModel rental = new RentalModel(
            LocalDate.now(),
            dto.getDaysRented(),
            originalPrice,
            customer.get(),
            game.get()
        );

        rentalRepository.save(rental);
        return Optional.of(rental);
    }

    public List<RentalModel> getRentals(){
        return rentalRepository.findAll();
    }
}
