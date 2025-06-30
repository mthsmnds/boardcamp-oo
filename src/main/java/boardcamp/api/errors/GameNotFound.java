package boardcamp.api.errors;

public class GameNotFound extends RuntimeException {
    public GameNotFound(String message){
        super(message);
    }
    
}
