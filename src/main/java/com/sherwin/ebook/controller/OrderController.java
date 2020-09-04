package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.*;
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
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
        }
        Order order = new Order();

        if (order == null) {
            order = new Order();
        }
        order.setStatus("open");
        order.setTax(cart.getTotalPrice() * 0.17);
        order.setDeliveryFee(cart.getTotalPrice() * 0.11);
        order.setTotalPrice(cart.getTotalPrice() * (1 + 0.17 + 0.11));
        order.setUser(user);
        order.addBooks(cart.getBooks());
        user.setCart(cart);
        userService.save(user);
        for (Book book : cart.getBooks()) {
            bookService.save(book);
        }
        orderService.save(order);

        Billing billing = order.getBilling();
        if (billing == null) {
            billing = new Billing();
//            order.setBilling(billing);
        }
        Delivery delivery = order.getDelivery();
        if (delivery == null) {
            delivery = new Delivery();
//            order.setDelivery(delivery);
        }
        System.out.println(billing);
        System.out.println(delivery);
        model.addAttribute("order", order);
        model.addAttribute("billing", billing);
        model.addAttribute("delivery", delivery);

        return "order/checkout";
    }

    @GetMapping("/order/placeorder")
    public String placeOrder(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
        }
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
//            User user = (User) authentication.getPrincipal();
//            user.setBilling(billing);
            User user = (User) authentication.getPrincipal();
            Cart cart = user.getCart();
            if (cart == null) {
                cart = new Cart();
            }

            Order order = new Order();
            order.setBilling(billing);
            orderService.save(order);
            billingService.save(billing);
//                redirectAttributes
//                        .addAttribute("id", newUser.getId())
//                        .addFlashAttribute("success", true);
            return "redirect:/order/checkout";
        }
    }

    @PostMapping("/order/delivery")
    public String getDelivery(@Valid Delivery delivery, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes,
                              Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("delivery", delivery);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "order/checkout";
        } else {
//            User user = (User) authentication.getPrincipal();
//            user.setDelivery(delivery);
            User user = (User) authentication.getPrincipal();
            Cart cart = user.getCart();
            if (cart == null) {
                cart = new Cart();
            }

            Order order = new Order();
            order.setDelivery(delivery);
            orderService.save(order);
            deliveryService.save(delivery);
//                redirectAttributes
//                        .addAttribute("id", newUser.getId())
//                        .addFlashAttribute("success", true);
            return "redirect:/order/checkout";
        }
    }

}
