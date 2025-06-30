package boardcamp.api.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomerCpfConflict.class})
    public ResponseEntity<String> handleCustomerCpfConflict(CustomerCpfConflict error){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error.getMessage());
    }

    @ExceptionHandler({CustomerNotFound.class})
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFound error){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
    }

    @ExceptionHandler({GameNameConflict.class})
    public ResponseEntity<String> handleGameNameConflict(GameNameConflict error){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error.getMessage());
    }

    @ExceptionHandler({GameNotFound.class})
    public ResponseEntity<String> handleGameNotFound(GameNotFound error){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
    }

    @ExceptionHandler({GameNotAvaiable.class})
    public ResponseEntity<String> handleGameNotAvaiable(GameNotAvaiable error){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error.getMessage());
    }

    @ExceptionHandler({RentalAlreadyFinished.class})
    public ResponseEntity<String> handleRentalAlreadyFinished(RentalAlreadyFinished error){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error.getMessage());
    }

    @ExceptionHandler({RentalNotFound.class})
    public ResponseEntity<String> handleRentalNotFound(RentalNotFound error){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
    }

    @ExceptionHandler({RentalNotFinished.class})
    public ResponseEntity<String> handleRentalNotFinished(RentalNotFinished error){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
    }
}
