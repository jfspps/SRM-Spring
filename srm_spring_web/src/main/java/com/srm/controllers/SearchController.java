package com.srm.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class SearchController {

    @GetMapping({"/search", "/search.html"})
    public String index() {

        //Thymeleaf finds and renders search.html
        return "search";
    }
}
