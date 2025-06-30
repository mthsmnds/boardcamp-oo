package boardcamp.api.errors;

public class RentalNotFound extends RuntimeException{
    public RentalNotFound(String message){
        super(message);
    }
}
