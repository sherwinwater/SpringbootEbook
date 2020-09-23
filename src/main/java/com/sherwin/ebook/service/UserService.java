package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.Role;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.repository.RoleRepository;
import com.sherwin.ebook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * register new user
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        encoder = new BCryptPasswordEncoder();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).get();
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id).get();
    }

    public void delete(Long id) {
        User user = userRepository.findUserById(id).get();
        for (Role role : user.getRoles()) {
            role.getUsers().remove(user);
//            roleRepository.save(role);
        }
        ;
        userRepository.deleteById(id);
    }

    public List<User> search(String content) {
        return userRepository.findByFirstNameOrLastNameOrEmailContaining(content, content, content);
    }

    public User register(User user) {
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
//        user.setEnabled(false);  //? need to activate
        user.setEnabled(true);  //
        user.setPassword(secret);
        user.setConfirmPassword(secret);
        user.setCart(new Cart());
        user.addRole(roleRepository.findByName("ROLE_USER"));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        return user;
    }

    public void updateUser(User user) {
        User existingUser = this.getUserById(user.getId());
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        String secret2 = "{bcrypt}" + encoder.encode(user.getConfirmPassword());

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAlias(user.getAlias());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(secret);
        existingUser.setConfirmPassword(secret);

        userRepository.save(existingUser);
    }

    @Transactional
    public void saveUsers(User... users) {
        for (User user : users) {
            logger.info("Saving user: " + user.getEmail());
            userRepository.save(user);
        }
    }

}
