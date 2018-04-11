package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TravelloController {

    @GetMapping("/journeys/")
    public ModelAndView listJourneys() {
        return null;
    }


}
