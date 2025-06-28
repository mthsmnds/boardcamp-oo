package boardcamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import boardcamp.api.models.RentalModel;

public interface RentalRepository extends JpaRepository<RentalModel, Long> {
        
}
