package boardcamp.api.errors;

public class RentalAlreadyFinished extends RuntimeException{
    public RentalAlreadyFinished(String message){
        super(message);
    }
    
}
