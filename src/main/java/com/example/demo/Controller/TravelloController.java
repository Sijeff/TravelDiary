package com.example.demo.Controller;

import com.example.demo.Repository.TravelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

@Controller
public class TravelloController {

    @Autowired
    private TravelloRepository travelloRepository;

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
}
