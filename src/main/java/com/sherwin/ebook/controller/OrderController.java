package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.Billing;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.Delivery;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.BillingService;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.service.CartService;
import com.sherwin.ebook.service.UserService;
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
    private BillingService billingService;

    @GetMapping("/order")
    public String getList(Model model) {

        return "order/checkout";
    }

    @GetMapping("/order/checkout")
    public String checkout(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = user.getCart();
        Billing billing = user.getBilling();
        Delivery delivery = user.getDelivery();
        model.addAttribute("cart", cart);
        model.addAttribute("billing", new Billing());
        model.addAttribute("delivery", delivery);

        return "order/checkout";
    }

    @PostMapping("/order/placeorder")
    public String placeOrder(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = user.getCart();
        model.addAttribute("cart", cart);

        return "order/placeorder";
    }

    @PostMapping("/order/billing")
    public String getBilling(@Valid Billing billing, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("billing", billing);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "order/checkout";
        } else {
            billingService.save(billing);
//            User newUser = userService.register(user);
//                redirectAttributes
//                        .addAttribute("id", newUser.getId())
//                        .addFlashAttribute("success", true);
            return "redirect:/order/checkout";
        }
    }


}
