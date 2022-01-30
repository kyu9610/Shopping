package Shop.Shopping.exception;

public class OutOfMoneyException extends RuntimeException{
    public OutOfMoneyException(String message){
        super(message);
    }
}
