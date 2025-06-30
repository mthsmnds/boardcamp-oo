package boardcamp.api.errors;

public class RentalNotFinished extends RuntimeException{
    public RentalNotFinished(String message){
        super(message);
    }
    
}
