package com.usta.opticavisionintegral.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionGeneratorController {

    @RequestMapping("/anotherService")
    public String generator() throws Exception{
        throw new Exception("exception");
    }
}
