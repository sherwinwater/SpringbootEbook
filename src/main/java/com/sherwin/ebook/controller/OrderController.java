package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.domain.Account;
import com.sherwin.ebook.service.*;
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
    private AccountService accountService;
    @Autowired
    private BookService bookService;

    @GetMapping("/order")
    public String getList(Model model) {

        return "order/checkout";
    }

    @GetMapping("/order/checkout")
    public String checkout(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()); //get data from db
        Cart cart = cartService.get(user);  //get data from db
        Account account = user.getAccount();
        Order order = orderService.getOpenOrder(user);
        if (order == null || order.getStatus().equals("placed")) {
            order = new Order();
        }
        orderService.addOrder(user, cart, order);

        Billing billing = order.getBilling();
        if (billing == null) {
            billing = account.getBilling()==null?new Billing():account.getBilling();
        }

        Delivery delivery = order.getDelivery();
        if (delivery == null) {
            delivery = account.getDelivery()==null?new Delivery():account.getDelivery();
        }
        model.addAttribute("order", order);
        model.addAttribute("billing", billing);
        model.addAttribute("delivery", delivery);

        return "order/checkout";
    }

    @GetMapping("/order/placeorder")
    public String placeOrder(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()); //get data from db
        Cart cart = cartService.get(user);  //get data from db
        Order order = orderService.getOpenOrder(user);

        orderService.placeOrder(user, cart, order);
        cartService.clearCart(cart);

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
            user = userService.getUserByEmail(user.getEmail()); //get data from db
            Order order = orderService.getOpenOrder(user);
            billingService.addBilling(order, billing);

            Account account = user.getAccount();
            accountService.addBilling(account, billing);

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
            user = userService.getUserByEmail(user.getEmail()); //get data from db
            Order order = orderService.getOpenOrder( user);
            deliveryService.addDelivery(order, delivery);
            Account account = user.getAccount();
            accountService.addDelivery(account, delivery);
//                redirectAttributes
//                        .addAttribute("id", newUser.getId())
//                        .addFlashAttribute("success", true);
            return "redirect:/order/checkout";
        }
    }

}
