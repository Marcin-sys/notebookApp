package pl.mirocha.marcin.it.notebook.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message){
        super(message);
    }
}
