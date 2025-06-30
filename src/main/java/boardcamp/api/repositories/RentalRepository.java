package boardcamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import boardcamp.api.models.RentalModel;

@Repository
public interface RentalRepository extends JpaRepository<RentalModel, Long> {
        @Query("SELECT COUNT (r) FROM RentalModel r WHERE r.game.id = :gameId AND r.returnDate IS NULL")
        int activeRentals(Long gameId);
}
