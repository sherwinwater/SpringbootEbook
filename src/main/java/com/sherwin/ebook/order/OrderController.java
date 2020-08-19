package com.sherwin.ebook.order;

import com.sherwin.ebook.book.BookService;
import com.sherwin.ebook.customer.Customer;
import com.sherwin.ebook.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookService bookService;

    @GetMapping("/order")
    public String getList(Model model) {
        Customer customer = customerService.get(1L);
        Optional<Order> orderOptional = orderService.get(customer);
        Order order = orderOptional.get();
        model.addAttribute("order", order);
        return "order/list";
    }

    @GetMapping("/order/checkoutCreditCard")
    public String getCreditCard(Model model) {
        Customer customer = customerService.get(1L);
        Optional<Order> orderOptional = orderService.get(customer);
        Order order = orderOptional.get();
        model.addAttribute("order", order);
        return "order/list";
    }



}
