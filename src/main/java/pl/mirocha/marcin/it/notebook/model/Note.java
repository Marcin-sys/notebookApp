package pl.mirocha.marcin.it.notebook.model;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private LocalDateTime dateTime;
//    private String userID;  //TODO

    public String getFormattedDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return this.dateTime.format(dateTimeFormatter);
    }
    public Note clone(){
        Note note = new Note();
        note.setNoteId(this.noteId);
        note.setNoteTitle(this.noteTitle);
        note.setNoteBody(this.noteBody);

        return note;
    }
}
