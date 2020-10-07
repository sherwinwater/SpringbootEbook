package com.sherwin.ebook.restcontroller;

import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
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
