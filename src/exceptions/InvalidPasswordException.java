package exceptions;

public class InvalidPasswordException extends Exception {
    public String message;
    public InvalidPasswordException(){
        this.message = "Invalid Password";
    }
}
