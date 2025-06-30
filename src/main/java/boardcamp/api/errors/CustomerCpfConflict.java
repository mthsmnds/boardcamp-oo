package boardcamp.api.errors;

public class CustomerCpfConflict extends RuntimeException{
    public CustomerCpfConflict(String message){
        super(message);
    }
}
