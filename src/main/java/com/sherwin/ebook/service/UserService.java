package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user){userRepository.save(user);}
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public Optional<User> get(String email){return userRepository.findUserByEmail(email);}
    public Optional<User> getByid(Long id){return userRepository.findUserById(id);}
    public void delete(Long id){userRepository.deleteById(id);}

    public List<User> search(String content) {
        return userRepository.findByFirstNameOrLastNameOrEmailContaining(content,content,content);
    }

}
