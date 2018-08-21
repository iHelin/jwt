package me.ianhe.jwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iHelin
 * @since 2018/7/21 11:12
 */
@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
