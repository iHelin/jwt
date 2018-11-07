package me.ianhe.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * UserDetailsServiceImpl
 *
 * @author iHelin
 * @since 2017/10/20 10:47
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户登录
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名:{}", username);
        return new User(username, passwordEncoder.encode("123456"), true, true, true, true, new ArrayList<>());
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("yuejusecret"));
        System.out.println(new BCryptPasswordEncoder().matches("123", "$2a$10$5Zwv03lLebe9wG8EdCxp9.2jL5vavfbXlkoNe50mplCCyMpMIoYmW"));
    }

}
