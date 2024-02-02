package pl.mirocha.marcin.it.notebook.exceptions;

public class NoteAlreadyExistException extends RuntimeException{
    public NoteAlreadyExistException(String message){
        super(message);
    }
}
