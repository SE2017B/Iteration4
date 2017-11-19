package exceptions;

public class InvalidNodeException extends Exception {
    public String message;
    public InvalidNodeException(String message){
        this.message = message;
    }
}
