package com.example.demo.Controller;

import com.example.demo.Domain.Journey;
import com.example.demo.Domain.Location;
import com.example.demo.Domain.User;
import com.example.demo.Repository.TravelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;


@Controller
public class TravelloController {

    @Autowired
    private TravelloRepository travelloRepository;

    @GetMapping("indexOld")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }

    @PostMapping("journeyform")
    public ModelAndView submitJourney(HttpSession session, @RequestParam String title, @RequestParam Date startDate, @RequestParam Date endDate, @RequestParam String text,
                                      @RequestParam float lat, @RequestParam float lng, @RequestParam String placeName, @RequestParam String country) {

        String username = (String) session.getAttribute("user");
        User user = travelloRepository.getUser(username);
        Journey journey = travelloRepository.addJourney(title, user);
        if (journey == null) {
            throw new NullPointerException("Journey was not initialized correctly");
        }

        if (!travelloRepository.verifyLocation(placeName, country)) {
            travelloRepository.addLocation(placeName, country, lng, lat);
        }

        Location location = travelloRepository.getLocation(placeName, country);
        travelloRepository.addJourneyPart(title, text, startDate, endDate, journey.getJourneyID(), location.getLocationID());


        return new ModelAndView("redirect:journeyconfirm");
    }

    @GetMapping("index")
    public ModelAndView gotoindex2() {
        return new ModelAndView("index").addObject("locations", travelloRepository.getLocations())
                .addObject("journeys", travelloRepository.listJourneys());
    }

    @GetMapping("/")
    public ModelAndView gotoempty() {
        return new ModelAndView("index").addObject("locations", travelloRepository.getLocations())
                .addObject("journeys", travelloRepository.listJourneys());
    }


    @GetMapping("registerUser")
    public ModelAndView gotoRegister() {
        return new ModelAndView("registerUser");
    }

    @GetMapping("journeyconfirm")
    public ModelAndView gotoJourneyConfirm() throws InterruptedException {
        return new ModelAndView("journeyconfirm");
    }

    @GetMapping("journeyform")
    public ModelAndView gotoJourneyform(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return new ModelAndView("journeyform");
        } else {
            return new ModelAndView("redirect:signin");
        }

    }

    @GetMapping("signin")
    public ModelAndView gotoSignin() {
        return new ModelAndView("signin");
    }

//    @GetMapping("/journey/")
//    public ModelAndView listJourneys() {
//        return new ModelAndView("journeys").addObject("journeys", travelloRepository.listJourneys());
//    }
    @GetMapping("/journey/")
    public ModelAndView cardListJourneys() {
        return new ModelAndView("journeys").addObject("journeys", travelloRepository.listJourneys());
    }

    @GetMapping("/journey/{journeyID}")
    public ModelAndView listJourneyParts(@PathVariable int journeyID) {
        Journey journey = travelloRepository.getJourney(journeyID);
        return new ModelAndView("journey")
                .addObject("journey", journey)
                .addObject("user", travelloRepository.getUserByJourney(journey))
                .addObject("journeyParts", travelloRepository.getJourneyPart(journey));
    }

    @PostMapping("/registerUser")
    public ModelAndView regUser(HttpSession session, @RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam Date birthday) {
        if (!email.toUpperCase().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")) {
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "Invalid email. Please try again.")
                    .addObject("enteredName", name)
                    .addObject("enteredDate", birthday);
        } else if (birthday.toLocalDate().compareTo(LocalDate.now()) >= 0) {
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "Birthday is invalid. Please try again.")
                    .addObject("enteredEmail", email)
                    .addObject("enteredName", name);
        } else if (!travelloRepository.checkUniqueUsername(name) || name.equals("")) {
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "Username taken or invalid. Please try again.")
                    .addObject("enteredEmail", email)
                    .addObject("enteredDate", birthday);
        } else if (password.equals("")) {
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "Please enter a password.")
                    .addObject("enteredName", name)
                    .addObject("enteredEmail", email)
                    .addObject("enteredDate", birthday);
        } else if (travelloRepository.checkDuplicateEmail(email)) {
            return new ModelAndView("registerUser")
                    .addObject("invalidInput", "This email is already registered. Please try again.")
                    .addObject("enteredName", name)
                    .addObject("enteredDate", birthday);
        } else {
            LocalDate regDate = LocalDate.now();
            session.setAttribute("user", name);
            travelloRepository.addUser(name, email, password, birthday, regDate);
        }
        return new ModelAndView("redirect:index").addObject("user", name);
    }

    @PostMapping("signin")
    public ModelAndView login(HttpSession session, HttpServletResponse res, @RequestParam String username, @RequestParam String password) {
        if (travelloRepository.verifyUser(username, password)) {

            User user = travelloRepository.getUser(username);
            session.setAttribute("user", username);
            session.setAttribute("userID", user.getUserID());

            return new ModelAndView("redirect:index").addObject("user", username);
        } else {
            session.removeAttribute("user");
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setMaxAge(0);
            res.addCookie(cookie);
            session.invalidate();
            return new ModelAndView("signin")
                    .addObject("invalidUser", "Invalid username or password");
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session, HttpServletResponse res) {
        session.removeAttribute("user");
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        session.invalidate();
        return "redirect:index";
    }


}
