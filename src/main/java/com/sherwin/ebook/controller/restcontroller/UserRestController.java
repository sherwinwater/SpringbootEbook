package com.sherwin.ebook.controller.restcontroller;

import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping("/usersrestdata")
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	@GetMapping("/user/search/{ajxsearchcontent}")
	public List<User> search(@PathVariable("ajxsearchcontent") String ajxsearchcontent){
		System.out.println(ajxsearchcontent);
		return  userService.search(ajxsearchcontent);
	}

	@GetMapping("/ajaxsearch")
	public List<User> showUser(Model model){
		List<User> results = userService.search("");
		model.addAttribute("users",results);
//        return "user/list :: table-refresh";
		return results;
	}

}
