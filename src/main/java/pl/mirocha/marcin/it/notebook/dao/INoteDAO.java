package pl.mirocha.marcin.it.notebook.dao;

import pl.mirocha.marcin.it.notebook.model.Note;
import pl.mirocha.marcin.it.notebook.model.User;

import java.util.List;

public interface INoteDAO {
    Note getById(int id);
    List<Note> getAll();
    List<Note> getByPattern(String pattern);
    Integer saveNoteAndReturnIdNote(Note note);
    void update(Note note);
    void delete(int id);
    List<Note> getAllUserNotes(User user);
    List<Note> getUserNotesByPattern(String pattern,List<Note> userNotes);
}
