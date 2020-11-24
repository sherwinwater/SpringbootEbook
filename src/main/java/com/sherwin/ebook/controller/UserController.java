package com.sherwin.ebook.controller;

import com.sherwin.ebook.service.UserService;
import com.sherwin.ebook.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String home(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "user/list";
    }

    @GetMapping("/user/{id}")
    public String viewUser(@PathVariable Long id,Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "admin/user/view";
    }

    @PostMapping("/user/search")
    public String search(@RequestParam("searchcontent") String searchcontent, Model model){
        List<User> search = userService.search(searchcontent);
        model.addAttribute("users",search);
        return "user/list";
    }

    @GetMapping("/user/ajaxsearch2")
    @ResponseBody
    public String showUser(Model model){
        return "3";
    }

    @GetMapping("/user/ajaxsearch3{searchcontent}")
    public String showUser2(@PathVariable String searchcontent, Model model){
        List<User> search = userService.search(searchcontent);
        model.addAttribute("users",search);
        return "user/list :: table-refresh";
    }

}
