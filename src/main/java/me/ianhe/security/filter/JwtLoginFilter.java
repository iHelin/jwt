//package me.ianhe.security.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.*;
//
//import static java.util.stream.Collectors.toList;
//
///**
// * @author iHelin
// * @since 2018/7/21 11:13
// */
//public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
//            throws AuthenticationException {
//        String username = obtainUsername(req);
//        String presentedPassword = obtainPassword(req);
//        if (presentedPassword == null) {
//            throw new BadCredentialsException("Bad credentials");
//        }
//        boolean ok = doLogin(username, presentedPassword);
//        if (!ok) {
//            throw new BadCredentialsException("Bad credentials");
//        }
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,
//                presentedPassword, new ArrayList<>());
//        return token;
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse response, FilterChain chain,
//                                            Authentication auth) throws IOException, ServletException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> res = new HashMap<>();
//        res.put("accessToken", createAccessToken(auth));
//        String r = objectMapper.writeValueAsString(res);
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        response.getWriter().print(r);
//    }
//
//    private String createAccessToken(Authentication auth) {
//        Optional.ofNullable(auth.getPrincipal()).orElseThrow(() -> new IllegalArgumentException("Cannot create Token without username"));
//        Optional.ofNullable(auth.getAuthorities()).orElseThrow(() -> new IllegalArgumentException("User doesn't have any privileges"));
//        Claims claims = Jwts.claims().setSubject((String) auth.getPrincipal());
//        claims.put("scopes", auth.getAuthorities().stream().map(Object::toString).collect(toList()));
//        LocalDateTime currentTime = LocalDateTime.now();
//        String accessToken = Jwts.builder()
//                .setClaims(claims)
//                .setIssuer("iHelin")
//                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
//                .setExpiration(Date.from(currentTime
//                        .plusMinutes(60L)
//                        .atZone(ZoneId.systemDefault()).toInstant()))
//                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
//                .compact();
//        logger.debug("accessToken claims:{}", claims);
//        return accessToken;
//    }
//
//    private boolean doLogin(String username, String password) {
//        return true;
//    }
//
//}
