package com.testing91.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tesla on 16/6/14.
 */

@Controller
public class BaselineController {

    @RequestMapping("/baseline")
    public String perfStandard() {

        return "addServer";
    }
}
