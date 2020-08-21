package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/user/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getByid(id).get());
        return "user/view";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        Optional<User> userOptional = userService.getByid(id);
        User user = userOptional.get();
        model.addAttribute("user", user);
        return "admin/userform";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/admin/user/create")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/userform";
    }

    @PostMapping("/admin/user/save")
    public String saveUser(@Valid User user, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userService.findAll());
            return "user/list";
        } else {
            userService.save(user);
            return "redirect:/admin/user/"+user.getId();
        }
    }

}
