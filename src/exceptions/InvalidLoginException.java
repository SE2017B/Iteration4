package exceptions;

public class InvalidLoginException extends Exception {
    String message;
    public InvalidLoginException(){
        this.message = "Invalid Login";
    }
}
