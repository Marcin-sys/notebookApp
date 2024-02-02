package pl.mirocha.marcin.it.notebook.dao.memory;

import org.springframework.stereotype.Component;
import pl.mirocha.marcin.it.notebook.dao.INoteDAO;
import pl.mirocha.marcin.it.notebook.model.Note;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class NoteRepository implements INoteDAO {

    private final List<Note> notes = new ArrayList<>();
    private final NoteIdSequence noteIdSequence;

    public NoteRepository(NoteIdSequence noteIdSequence) {
        this.notes.add(new Note(noteIdSequence.getId(), "FirstNote", "NoteBody"));
        this.notes.add(new Note(noteIdSequence.getId(), "FirstNote2", "NoteBody2"));
        this.notes.add(new Note(noteIdSequence.getId(), "FirstNote3", "NoteBody3"));
        this.noteIdSequence = noteIdSequence;
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
    public void save(Note note) {
        note.setNoteId(this.noteIdSequence.getId());
        this.notes.add(note);
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
