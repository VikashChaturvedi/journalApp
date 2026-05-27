package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.Response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String greeting="";
        final WeatherResponse weatherResponse = weatherService.getWeather("Pune");
        if(weatherResponse!=null)
        {
            greeting=" , Weather feels like " + weatherResponse.getCurrent().getTempC();
        }
        return new ResponseEntity<>("Hi "+ authentication.getName()+ greeting, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        if (userInDb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Only allow updating safe, self-owned fields. Username (identity) and roles
        // must never be modified through this endpoint.
        boolean changed = false;
        if (StringUtils.hasText(user.getPassword())) {
            userInDb.setPassword(userService.encodePassword(user.getPassword()));
            changed = true;
        }
        if (StringUtils.hasText(user.getEmail())) {
            userInDb.setEmail(user.getEmail());
            changed = true;
        }

        if (!changed) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserByUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
