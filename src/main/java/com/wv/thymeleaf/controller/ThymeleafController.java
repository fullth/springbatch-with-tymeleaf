package com.wv.thymeleaf.controller;

import com.wv.thymeleaf.repository.thymeleaf.Thymeleaf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/Thymeleaf")
public class ThymeleafController {

    @GetMapping("/test")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("test/ThymeleafTest");

        Thymeleaf thymeleaf = new Thymeleaf(1, "Tester");
        modelAndView.addObject("test", thymeleaf);

        System.out.println(thymeleaf);
        return modelAndView;
    }
}
