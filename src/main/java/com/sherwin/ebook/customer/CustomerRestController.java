package com.sherwin.ebook.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customersrestdata")
	public List<Customer> getAllCustomers() {
		return customerService.findAll();
	}

	@GetMapping("/search/{ajxsearchcontent}")
	public List<Customer> search(@PathVariable("ajxsearchcontent") String ajxsearchcontent){
		System.out.println(ajxsearchcontent);
		return  customerService.search(ajxsearchcontent);
	}

	@GetMapping("/ajaxsearch")
	public List<Customer> showCustomer(Model model){
		List<Customer> results = customerService.search("");
		model.addAttribute("customers",results);
//        return "customer/list :: table-refresh";
		return results;
	}

}
