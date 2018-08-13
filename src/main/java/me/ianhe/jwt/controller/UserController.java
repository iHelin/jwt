package me.ianhe.jwt.controller;

import me.ianhe.jwt.entity.MyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iHelin
 * @since 2018/7/21 11:12
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/signup")
    public String signUp(@RequestBody MyUser user) {
        logger.debug(user.getPassword());
        return "注册成功";
    }
}
