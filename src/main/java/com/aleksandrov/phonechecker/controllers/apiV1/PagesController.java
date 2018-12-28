package com.aleksandrov.phonechecker.controllers.apiV1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {
    @RequestMapping({"/", "", "index.html", "home"})
    public String index() {
        return "index";
    }

    @RequestMapping("/admin-panel")
    public String admin() {
        return "admin-panel";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
