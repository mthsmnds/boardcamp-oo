package boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boardcamp.api.dtos.RentalDTO;
import boardcamp.api.models.RentalModel;
import boardcamp.api.services.RentalService;
import jakarta.validation.Valid;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/rentals")
public class RentalController {
    final RentalService rentalService;

    RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    @GetMapping()
    public ResponseEntity<Object> getRentals(){
            return ResponseEntity.status(HttpStatus.OK).body(rentalService.getRentals());
        }

    @PostMapping()
    public ResponseEntity<Object> postRental(@RequestBody @Valid RentalDTO body) {
        RentalModel rental = rentalService.postRental(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }
    
    @PostMapping("/{id}/return")
        public ResponseEntity<Object> returnRental(@PathVariable Long id){
            RentalModel rental = rentalService.returnRental(id);
            return ResponseEntity.ok(rental);

    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Object> deleteRental(@PathVariable Long id) {
            rentalService.deleteRental(id);
            return ResponseEntity.ok().build();
    }
}
