package pl.mirocha.marcin.it.notebook.controllers;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mirocha.marcin.it.notebook.dao.IUserDAO;
import pl.mirocha.marcin.it.notebook.exceptions.UserAlreadyExistException;
import pl.mirocha.marcin.it.notebook.exceptions.UserValidationException;
import pl.mirocha.marcin.it.notebook.model.User;
import pl.mirocha.marcin.it.notebook.model.dto.RegisterUserDTO;
import pl.mirocha.marcin.it.notebook.validators.UserValidator;

@Controller
public class AuthenticationController {
    private final IUserDAO userDAO;

    public AuthenticationController(IUserDAO userDAO) {
        this.userDAO = userDAO;
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
        if (user != null && user.getPassword().equals(DigestUtils.md5Hex(password))) {
            user.setPassword(null);
            httpSession.setAttribute("user", user);
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
        return user;
    }
}
