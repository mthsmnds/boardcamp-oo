package boardcamp.api.errors;

public class InvalidRentalRequest extends RuntimeException{
    public InvalidRentalRequest(String message){
        super(message);
    }
    
}
