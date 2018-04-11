package com.example.demo.Controller;

import com.example.demo.Repository.TravelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;


@Controller
public class TravelloController {

    @Autowired
    private TravelloRepository travelloRepository;

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/journeys/")
    public ModelAndView listJourneys() {
        return null;
    }

    @GetMapping("registerUser")
    public ModelAndView gotoRegister() {
        return new ModelAndView("registerUser");
    }

    @PostMapping("/registerUser")
    public ModelAndView regUser(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam Date birthday) {
        LocalDate regDate = LocalDate.now();
        travelloRepository.addUser(name,email,password,birthday,regDate);

        return new ModelAndView("error");
    }

    @PostMapping("signin")
    public ModelAndView login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        if (travelloRepository.verifyUser(username, password)) {
            session.setAttribute("user", username);
            return new ModelAndView("index").addObject("user", username);
        } else {
            return new ModelAndView("signin");
        }
    }

    @PostMapping("logout")
    public String logout (HttpSession session) {
        session.removeAttribute("user");

        return "index";
    }
}
