package pl.mirocha.marcin.it.notebook.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Note {
    private int noteId;
    private String noteTitle;
    private String noteBody;

    public Note clone(){
        Note note = new Note();
        note.setNoteId(this.noteId);
        note.setNoteTitle(this.noteTitle);
        note.setNoteBody(this.noteBody);
        return note;
    }
}
