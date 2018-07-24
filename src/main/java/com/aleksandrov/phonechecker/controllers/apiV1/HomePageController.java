package com.aleksandrov.phonechecker.controllers.apiV1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HomePageController {
    @RequestMapping({"/", "", "index.html", "home"})
    public String index(Map<String, Object> model) {
        return "index";
    }
}
