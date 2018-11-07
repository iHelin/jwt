package me.ianhe.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iHelin
 * @since 2017/10/16 09:14
 */
@RestController
public class MainController {

    @GetMapping({"", "index"})
    public String index() {
        return "index";
    }

}
