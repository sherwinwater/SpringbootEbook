package com.sherwin.ebook.admin;

import com.sherwin.ebook.customer.Customer;
import com.sherwin.ebook.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/admin/customer/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.get(id));
        return "customer/view";
    }

    @GetMapping("/admin/customer/edit/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.get(id);
        model.addAttribute("customer", customer);
        return "admin/customerform";
    }

    @GetMapping("/admin/customer/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model) {
        customerService.delete(id);
        return "redirect:/customers";
    }

    @GetMapping("/admin/customer/create")
    public String createCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "admin/customerform";
    }

    @PostMapping("/admin/customer/save")
    public String saveCustomer(@Valid Customer customer, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customerService.findAll());
            return "customer/list";
        } else {
            customerService.save(customer);
            return "redirect:/admin/customer/"+customer.getId();
        }
    }

}
