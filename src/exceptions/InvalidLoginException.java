package exceptions;

public class InvalidLoginException extends Exception {
    public String message;
    public InvalidLoginException(){
        this.message = "Invalid Password";
    }
}
