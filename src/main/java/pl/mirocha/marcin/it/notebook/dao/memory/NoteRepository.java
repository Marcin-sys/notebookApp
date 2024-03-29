package pl.mirocha.marcin.it.notebook.dao.memory;

import org.springframework.stereotype.Component;
import pl.mirocha.marcin.it.notebook.dao.INoteDAO;
import pl.mirocha.marcin.it.notebook.model.Note;
import pl.mirocha.marcin.it.notebook.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class NoteRepository implements INoteDAO {

    private final List<Note> notes = new ArrayList<>();
    private final IdSequence idSequence;

    public NoteRepository(IdSequence idSequence) {
        this.idSequence = idSequence;
    }

    @Override
    public Note getById(int id) {
        for (Note note : this.notes) {
            if (note.getNoteId() == id) {
                return note.clone();
            }
        }
        return null;
    }
    @Override
    public List<Note> getAllUserNotes(User user) {
        List<Note> result = new ArrayList<>();
        for (Note note : this.notes){
            if (user.getAccessibleListNotesById().contains(note.getNoteId())){
                result.add(note);
            }
        }
        return result;
    }

    @Override
    public List<Note> getUserNotesByPattern(String pattern,List<Note> userNotes) {
        List<Note> result = new ArrayList<>();
        for (Note note : userNotes) {
            if (note.getNoteTitle().toLowerCase().contains(pattern.toLowerCase())
                    || note.getNoteBody().toLowerCase().contains(pattern.toLowerCase())) {
                result.add(note.clone());
            }
        }
        return result;
    }


    @Override
    public List<Note> getAll() {
        List<Note> result = new ArrayList<>();
        for (Note note : this.notes) {
            result.add(note.clone());
        }
        return result;
    }

    @Override
    public List<Note> getByPattern(String pattern) {
        List<Note> result = new ArrayList<>();
        for (Note note : this.notes) {
            if (note.getNoteTitle().toLowerCase().contains(pattern.toLowerCase())
                    || note.getNoteBody().toLowerCase().contains(pattern.toLowerCase())) {
                result.add(note.clone());
            }
        }
        return result;
    }

    @Override
    public Integer saveNoteAndReturnIdNote(Note note) {
        note.setNoteId(this.idSequence.getId());
        this.notes.add(note);
        return note.getNoteId();
    }

    @Override
    public void update(Note note) {
        Note noteFromDB = this.getById(note.getNoteId());
        if (noteFromDB == null) {
            return;
        }
        noteFromDB.setNoteTitle(note.getNoteTitle());
        noteFromDB.setNoteBody(note.getNoteBody());
    }

    @Override
    public void delete(int id) {
        Iterator<Note> iterator = this.notes.iterator();
        while (iterator.hasNext()) {
            Note note = iterator.next();
            if (note.getNoteId() == id) {
                iterator.remove();
                break;
            }
        }
    }
}
