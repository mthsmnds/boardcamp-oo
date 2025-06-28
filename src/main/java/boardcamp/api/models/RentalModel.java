package boardcamp.api.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rentals")
public class RentalModel {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDate rentDate;

    @Column(nullable = false)
    private int daysRented;

    @Column(nullable = true)
    private LocalDate returnDate;

    @Column(nullable = false)
    private int originalPrice;

    @Column(nullable = false)
    private int delayFee;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "gameId", nullable = false)
    private GameModel game;

    public RentalModel(LocalDate rentDate, int daysRented, int originalPrice, CustomerModel customer, GameModel game) {
        this.rentDate = rentDate;
        this.daysRented = daysRented;
        this.returnDate = null;
        this.originalPrice = originalPrice;
        this.delayFee = 0;
        this.customer = customer;
        this.game = game;
    }
}
