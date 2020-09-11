package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.domain.account.Account;
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
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

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

    @GetMapping("/account")
    public String getAccountHome() {
        return "account/home";
    }

    @GetMapping("/account/billing")
    public String getBillingHome(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
        Billing billing = user.getAccount().getBilling();
        if (billing == null) {
            billing = new Billing();
        }
        model.addAttribute("billing", billing);

        return "account/billing";
    }

    @PostMapping("/account/billing")
    public String getBilling(@Valid Billing billing, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes,
                             Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("billing", billing);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "account/billing";
        } else {
            User user = (User) authentication.getPrincipal();
            user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
            Account account = user.getAccount();
            accountService.addBilling(account,billing);
            return "redirect:/account/billing";
        }
    }

    @PostMapping("/account/delivery")
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
            Order order = orderService.getOpenOrder("open",user);

            deliveryService.addDelivery(order,delivery);
//                redirectAttributes
//                        .addAttribute("id", newUser.getId())
//                        .addFlashAttribute("success", true);
            return "redirect:/order/checkout";
        }
    }

}
