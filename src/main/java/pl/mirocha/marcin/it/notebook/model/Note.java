package pl.mirocha.marcin.it.notebook.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Note {
    private int id;
    private String noteTitle;
    private String noteBody;

    public Note clone(){
        Note note = new Note();
        note.setId(this.id);
        note.setNoteTitle(this.noteTitle);
        note.setNoteBody(this.noteBody);
        return note;
    }
}
