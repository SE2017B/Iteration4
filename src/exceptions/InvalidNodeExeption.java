package exceptions;

public class InvalidNodeExeption extends Exception {
    public String message;
    public InvalidNodeExeption(){
        this.message = "Invalid Node";
    }
}
