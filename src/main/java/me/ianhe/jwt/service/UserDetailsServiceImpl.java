package me.ianhe.jwt.service;

import me.ianhe.jwt.entity.MyUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

/**
 * @author iHelin
 * @since 2018/7/21 11:18
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = new MyUser("admin","password");
//        if (myUser == null) {
//            throw new UsernameNotFoundException(username);
//        }
        return new User(myUser.getUsername(), myUser.getPassword(), emptyList());
    }
}
