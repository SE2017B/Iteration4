package DepartmentSubsystem.Exceptions;

public class UsernameException extends Exception {
    public UsernameException(){
        super("Username not found");
    }
}
