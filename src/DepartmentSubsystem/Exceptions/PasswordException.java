package DepartmentSubsystem.Exceptions;

public class PasswordException extends Exception {
    public PasswordException(){
        super("Password not found");
    }
}
