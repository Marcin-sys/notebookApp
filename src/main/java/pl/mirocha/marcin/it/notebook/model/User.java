package pl.mirocha.marcin.it.notebook.model;


import lombok.*;
import pl.mirocha.marcin.it.notebook.dao.memory.NoteRepository;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private NoteRepository userRepositoryNotes;

    @Override
    public User clone() {
        User user = new User();
        user.setId(this.id);
        user.setLogin(this.login);
        user.setPassword(this.password);
        user.setName((this.name));
        user.setSurname(this.surname);
        user.setRole(this.role);
        user.setUserRepositoryNotes(this.userRepositoryNotes);
        return user;
    }

    public enum Role {
        USER,
        ADMIN;
    }
}
