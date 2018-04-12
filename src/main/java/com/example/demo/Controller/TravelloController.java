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

    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }



    @GetMapping("registerUser")
    public ModelAndView gotoRegister() {
        return new ModelAndView("registerUser");
    }

    @GetMapping("journeyform")
    public ModelAndView gotoJourneyform() {
        return new ModelAndView("journeyform");
    }

    @GetMapping("signin")
    public ModelAndView gotoSignin(){
        return new ModelAndView("signin");
    }

    @PostMapping("/registerUser")
    public ModelAndView regUser(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam Date birthday) {
        if (!email.toUpperCase().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")){
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "Invalid email. Please try again.")
                    .addObject("enteredName",name)
                    .addObject("enteredDate", birthday);
        }else if(birthday.toLocalDate().compareTo(LocalDate.now())>= 0){
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "Birthday is invalid. Please try again.")
                    .addObject("enteredEmail",email)
                    .addObject("enteredName",name);
        }else if(!travelloRepository.checkUniqueUsername(name) || name.equals("")){
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "Username taken or invalid. Please try again.")
                    .addObject("enteredEmail",email)
                    .addObject("enteredDate", birthday);
        }else if(password.equals("")){
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "Please enter a password.")
                    .addObject("enteredName",name)
                    .addObject("enteredEmail",email)
                    .addObject("enteredDate", birthday);
        }else if(travelloRepository.checkDuplicateEmail(email)){
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "This email is already registered. Please try again.")
                    .addObject("enteredName",name)
                    .addObject("enteredDate", birthday);
        }else {
            LocalDate regDate = LocalDate.now();
            travelloRepository.addUser(name, email, password, birthday, regDate);
        }
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

    //@PostMapping("journeyform")

}
