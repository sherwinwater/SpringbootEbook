package com.sherwin.ebook.controller;

import com.sherwin.ebook.service.CustomerService;
import com.sherwin.ebook.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public String home(Model model){
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers",customers);
        return "customer/list";
    }

    @GetMapping("/customer/{id}")
    public String viewCustomer(@PathVariable Long id,Model model){
        Customer customer = customerService.get(id);
        model.addAttribute("customer",customer);
        return "customer/view";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searchcontent") String searchcontent, Model model){
        List<Customer> search = customerService.search(searchcontent);
        model.addAttribute("customers",search);
        return "customer/list";
    }

    @GetMapping("/ajaxsearch2")
    @ResponseBody
    public String showCustomer(Model model){
        return "3";
    }

    @GetMapping("/ajaxsearch3/{searchcontent}")
    public String showCustomer2(@PathVariable String searchcontent, Model model){
        List<Customer> search = customerService.search(searchcontent);
        model.addAttribute("customers",search);
        return "customer/list :: table-refresh";
    }

}
