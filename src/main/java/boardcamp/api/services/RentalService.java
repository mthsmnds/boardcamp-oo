package boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import boardcamp.api.dtos.RentalDTO;
import boardcamp.api.errors.CustomerNotFound;
import boardcamp.api.errors.GameNotAvaiable;
import boardcamp.api.errors.GameNotFound;
import boardcamp.api.errors.InvalidRentalRequest;
import boardcamp.api.errors.RentalAlreadyFinished;
import boardcamp.api.errors.RentalNotFinished;
import boardcamp.api.errors.RentalNotFound;
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

    public RentalModel postRental(RentalDTO dto){
        CustomerModel customer = customerRepository
        .findById(dto.getCustomerId())
        .orElseThrow(()-> new CustomerNotFound("Cliente não encontrado"));

        GameModel game = gameRepository
        .findById(dto.getGameId())
        .orElseThrow(()-> new GameNotFound("Jogo não encontrado"));
        
        if(dto.getDaysRented() <= 0){
            throw new InvalidRentalRequest("O número de dias deve ser maior que zero");
        }

        int activeRentals = rentalRepository.activeRentals(dto.getGameId());
        if(activeRentals >= game.getStockTotal()){
            throw new GameNotAvaiable("Jogo indisponível no estoque");
        }

        int pricePerDay = game.getPricePerDay();
        int originalPrice = pricePerDay * dto.getDaysRented();

        RentalModel rental = new RentalModel(
            LocalDate.now(),
            dto.getDaysRented(),
            originalPrice,
            customer,
            game
        );

        return rentalRepository.save(rental);
    }

    public List<RentalModel> getRentals(){
        return rentalRepository.findAll();
    }

    public RentalModel returnRental(Long id) {
        RentalModel rental = rentalRepository
            .findById(id)
            .orElseThrow(() -> new RentalNotFound("Aluguel não encontrado"));

        if (rental.getReturnDate() != null) {
            throw new RentalAlreadyFinished("Este aluguel já foi finalizado");
        }

        LocalDate returnDate = LocalDate.now();
        rental.setReturnDate(returnDate);

        LocalDate expectedReturn = rental.getRentDate().plusDays(rental.getDaysRented());
        long daysLate = ChronoUnit.DAYS.between(expectedReturn, returnDate);
        
        if(daysLate > 0){
            int delayFee = (int)(daysLate * rental.getGame().getPricePerDay());
            rental.setDelayFee(delayFee);
        }

        return rentalRepository.save(rental);
}

    public void deleteRental(Long id) {
    RentalModel rental = rentalRepository.findById(id)
        .orElseThrow(() -> new RentalNotFound("Aluguel não encotrado"));

    if (rental.getReturnDate() == null) {
        throw new RentalNotFinished("Não é possível excluir um aluguel ativo");
    }

    rentalRepository.delete(rental);
}
}
