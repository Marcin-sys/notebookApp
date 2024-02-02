package pl.mirocha.marcin.it.notebook.controllers;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mirocha.marcin.it.notebook.dao.INoteDAO;
import pl.mirocha.marcin.it.notebook.dao.IUserDAO;
import pl.mirocha.marcin.it.notebook.dao.memory.NoteIdSequence;
import pl.mirocha.marcin.it.notebook.dao.memory.NoteRepository;
import pl.mirocha.marcin.it.notebook.exceptions.UserAlreadyExistException;
import pl.mirocha.marcin.it.notebook.exceptions.UserValidationException;
import pl.mirocha.marcin.it.notebook.model.Note;
import pl.mirocha.marcin.it.notebook.model.User;
import pl.mirocha.marcin.it.notebook.model.dto.RegisterUserDTO;
import pl.mirocha.marcin.it.notebook.validators.UserValidator;

@Controller
public class CommonController {
    private final IUserDAO userDAO;
    private INoteDAO noteDAO;

    public CommonController(IUserDAO userDAO, INoteDAO noteDAO) {
        this.userDAO = userDAO;
        this.noteDAO = noteDAO;
    }

    @RequestMapping(path = {"/main", "/", "/index"}, method = RequestMethod.GET)
    public String main(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") instanceof String) {
            String userName = httpSession.getAttribute("userName").toString();
            User user = this.userDAO.getByLogin(userName);
            if (httpSession.getAttribute("filter") instanceof String) {
                String pattern = httpSession.getAttribute("filter").toString();
                model.addAttribute("notes", user.getUserRepositoryNotes().getByPattern(pattern));
            } else {
                model.addAttribute("notes", user.getUserRepositoryNotes().getAll());
            }
        }
        return "index";
    }

    @RequestMapping(path = "/newNote", method = RequestMethod.POST)
    public String newNote(@RequestParam String titleBody, @RequestParam String notBookBody, Model model,
                          HttpSession httpSession) {
        Note note = new Note();
        note.setNoteTitle(titleBody);
        note.setNoteBody(notBookBody);
        String userName = httpSession.getAttribute("userName").toString();
        User user = this.userDAO.getByLogin(userName);
        user.getUserRepositoryNotes().save(note);
        model.addAttribute("notes", user.getUserRepositoryNotes().getAll());
        return "redirect:/main";
    }

    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    public String filter(@RequestParam String pattern, Model model, HttpSession httpSession) {
        if (pattern.isEmpty()) {
            httpSession.removeAttribute("filter");
        } else {
            httpSession.setAttribute("filter", pattern);
        }
        return "redirect:/main";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String login, @RequestParam String password,
                        HttpSession httpSession) {
        try {
            UserValidator.validateLogin(login);
            UserValidator.validatePassword(password);
        } catch (UserValidationException e) {
            return "redirect:/login";
        }
        User user = this.userDAO.getByLogin(login);
        String userName = login;
        if (user != null && user.getPassword().equals(DigestUtils.md5Hex(password))) {
            user.setPassword(null);
            httpSession.setAttribute("user", user);
            httpSession.setAttribute("userName",login);
            return "redirect:/main";
        }
        return "redirect:/login";
    }
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        httpSession.setAttribute("user", null);
        return "redirect:/main";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("userModel", new RegisterUserDTO());
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register2(@ModelAttribute RegisterUserDTO userDTO) {
        try {
            UserValidator.validateUserDTO(userDTO);
            this.userDAO.save(map(userDTO));
        } catch (UserAlreadyExistException | UserValidationException e) {
            return "redirect:/register";
        }
        return "redirect:/main";
    }

    private User map(RegisterUserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setLogin(userDTO.getLogin());
        user.setPassword(DigestUtils.md5Hex(userDTO.getPassword()));
        user.setRole(User.Role.USER);
        user.setUserRepositoryNotes(new NoteRepository(new NoteIdSequence()));
        return user;
    }


}
