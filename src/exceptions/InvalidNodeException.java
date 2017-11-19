package exceptions;

public class InvalidNodeException extends Exception {
    public String message;
    public InvalidNodeException(){
        this.message = "Invalid Node";
    }
}
