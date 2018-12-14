package me.ianhe.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author iHelin
 * @date 2018-11-30 15:47
 */
@EnableWebSecurity(debug = true)
public class SercurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/").hasAnyRole("USER", "ADMIN")
                .antMatchers("/test").hasRole("ADMIN")
                .and().formLogin().loginProcessingUrl("/login").permitAll()
                .failureHandler(authenticationFailureHandler).successHandler(authenticationSuccessHandler).and()
//                .logout().permitAll().and()
                .exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("123456").roles("USER").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("123456").roles("ADMIN").build());
        return manager;
    }

}
