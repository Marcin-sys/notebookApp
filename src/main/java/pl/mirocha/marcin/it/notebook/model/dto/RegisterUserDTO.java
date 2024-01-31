package pl.mirocha.marcin.it.notebook.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RegisterUserDTO {
    private String name;
    private String surname;
    private String login;
    private String password;
    private String password2;

}
