package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.service.*;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BillingService billingService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private BookService bookService;

    @GetMapping("/order")
    public String getList(Model model) {

        return "order/checkout";
    }

    @GetMapping("/order/checkout")
    public String checkout(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
        Cart cart = cartService.get(user);  //get data from db
        Order order = cart.getOrder();
        if (order == null) {
            order = new Order();
        }
        orderService.addOrder(user, cart, order);

        Billing billing = order.getBilling();
        if (billing == null) {
            billing = new Billing();
        }
//        System.out.println(billing.getPayment());

        Delivery delivery = order.getDelivery();
        if (delivery == null) {
            delivery = new Delivery();
        }
        model.addAttribute("order", order);
        model.addAttribute("billing", billing);
        model.addAttribute("delivery", delivery);

        return "order/checkout";
    }

    @GetMapping("/order/placeorder")
    public String placeOrder(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
        Cart cart = cartService.get(user);  //get data from db

        cartService.clearCart(cart);
        cartService.deleteCart(cart);
        return "order/placeorder";
    }

    @PostMapping("/order/billing")
    public String getBilling(@Valid Billing billing, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes,
                             Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("billing", billing);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "order/checkout";
        } else {
            User user = (User) authentication.getPrincipal();
            user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
            Cart cart = cartService.get(user);  //get data from db
            Order order = cart.getOrder();
            billingService.addBilling(order,billing);
            return "redirect:/order/checkout";
        }
    }

    @PostMapping("/order/delivery")
    public String getDelivery(@Valid Delivery delivery, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes,
                              Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while adding delivery info");
            model.addAttribute("delivery", delivery);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "order/checkout";
        } else {
            User user = (User) authentication.getPrincipal();
            user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
            Cart cart = cartService.get(user);  //get data from db
            Order order = cart.getOrder();
            deliveryService.addDelivery(order,delivery);
//                redirectAttributes
//                        .addAttribute("id", newUser.getId())
//                        .addFlashAttribute("success", true);
            return "redirect:/order/checkout";
        }
    }

}
