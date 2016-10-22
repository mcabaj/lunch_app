package com.grapeup.web.security;

import com.grapeup.domain.User;
import com.grapeup.dto.CredentialsDto;
import com.grapeup.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthUserProvider authUserProvider;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/plain")
    @ResponseBody
    public String login(@RequestBody CredentialsDto credentials, HttpServletResponse response) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            log.debug("User '{}' found; generating auth token", username);
            String token = authUserProvider.generateToken(user);
            response.setStatus(HttpStatus.OK.value());
            return token;
        } else {
            log.warn("User '{}' not found; username or password is invalid", username);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return "";
        }
    }

    @RequestMapping(value = "/logout")
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                String username = userDetails.getUsername();
                log.debug("Logout user: '{}'", username);
                authUserProvider.removeAuthUser(username);

                SecurityContextHolder.getContext().setAuthentication(null);
                SecurityContextHolder.clearContext();
            }
        }
    }
}
