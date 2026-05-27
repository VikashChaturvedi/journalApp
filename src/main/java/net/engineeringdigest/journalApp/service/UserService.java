package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

       // public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public PasswordEncoder passwordEncoder;

    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            // below we are using the placeholder---> {}, instead of concatenating string using functionality of placeholder
            log.error("Error occurred for user {}:",user.getUserName(),e);
            return false;
        }
    }
    public void saveAdminUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }



    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> searchById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String UserName) {
        return userRepository.findByUserName(UserName);
    }
}


// controller----->servie----->repopsitory