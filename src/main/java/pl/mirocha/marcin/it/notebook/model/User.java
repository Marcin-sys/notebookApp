package pl.mirocha.marcin.it.notebook.model;


import lombok.*;

import java.util.List;

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
    private List<Integer> accessibleListNotesById;

    @Override
    public User clone() {
        User user = new User();
        user.setId(this.id);
        user.setLogin(this.login);
        user.setPassword(this.password);
        user.setName((this.name));
        user.setSurname(this.surname);
        user.setRole(this.role);
        user.setAccessibleListNotesById(this.accessibleListNotesById);
        return user;
    }

    public enum Role {
        USER,
        ADMIN;
    }
}
