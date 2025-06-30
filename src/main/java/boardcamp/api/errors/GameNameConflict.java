package boardcamp.api.errors;

public class GameNameConflict extends RuntimeException {
    public GameNameConflict(String message){
        super(message);
    } 
    
}
