package pl.mirocha.marcin.it.notebook.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

    @RequestMapping(path = {"/main", "/", "/index"}, method = RequestMethod.GET)
    public String main() {
        return "index";
    }
}
