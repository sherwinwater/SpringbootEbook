package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.domain.Account;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return "redirect:/account/billing";
    }

    @GetMapping("/account/billing")
    public String getBillingInfo(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()); //get data from db
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
            user = userService.getUserByEmail(user.getEmail()); //get data from db
            Account account = user.getAccount();
            accountService.addBilling(account, billing);
            return "redirect:/account/billing";
        }
    }

    @GetMapping("/account/delivery")
    public String getDeliveryInfo(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()); //get data from db
        Delivery delivery = user.getAccount().getDelivery();
        if (delivery == null) {
            delivery = new Delivery();
        }
        model.addAttribute("delivery", delivery);

        return "account/delivery";
    }

    @PostMapping("/account/delivery")
    public String getDelivery(@Valid Delivery delivery, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes,
                              Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while adding delivery info");
            model.addAttribute("delivery", delivery);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "account/delivery";
        } else {
            User user = (User) authentication.getPrincipal();
            user = userService.getUserByEmail(user.getEmail()); //get data from db
            Account account = user.getAccount();
            accountService.addDelivery(account, delivery);
//                redirectAttributes
//                        .addAttribute("id", newUser.getId())
//                        .addFlashAttribute("success", true);
            return "redirect:/account/delivery";
        }
    }

    @PostMapping("/account/favorite/{id}")
    public String addBook(@PathVariable Long id,
                          Authentication authentication, HttpSession session) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            user = userService.getUserByEmail(user.getEmail()); //get data from db
            Account account = user.getAccount();
            accountService.addFavorite(account, id);
        } else {
            User guest = (User) session.getAttribute("guest");
            if (guest == null) {
                guest = new User("guest");
                session.setAttribute("guest", guest);
                guest.setCart(new Cart());
            }
            Cart cart = guest.getCart();
//            cartService.addGuestBook(id, quantity, cart);
        }
        return "redirect:/books";
    }

    @GetMapping("/account/favorite")
    public String getFavorite(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()); //get data from db
        Account account = user.getAccount();
        Favorite favorite = account.getFavorite();
        if (favorite == null) {
            favorite = new Favorite();
        }
        model.addAttribute("favorite", favorite);
        return "account/favorite";
    }


    @GetMapping("/account/favorite/delete/{id}")
    public String deleteBook(@PathVariable Long id, Authentication authentication, HttpSession session) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()); //get data from db
        Account account = user.getAccount();
        Favorite favorite = account.getFavorite();
        accountService.deleteFavorite(account, favorite, id);
        return "redirect:/account/favorite";
    }

    @GetMapping("/account/order")
    public String getOrder(Model model, Authentication authentication,
                           HttpSession session) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()); //get data from db
//        Set<Order> orders = user.getOrders();
//        if (orders.isEmpty()) {
//            orders = new HashSet<>();
//        } else {
//            for (Order order : orders) {
//                orders = new HashSet<>(orders);
//                if (order.getStatus().equals("open")) {
//                    orders.remove(order);
//                }
//            }
//        }
        String[] statuses = {"open", "placed", "delivery", "closed"};

        Order order = (Order) session.getAttribute("order");
        List<Order> orders = (List<Order>) session.getAttribute("orders");
        if (orders == null) {
            orders = new ArrayList<>();
        }
        if (order == null) {
            order = new Order();
        }

        model.addAttribute("statuses", statuses);
        model.addAttribute("orders", orders);
        model.addAttribute("order", order);
        return "account/order";
    }

//    @PostMapping("/account/order/search")
//    public String searchOrder(@Valid Order order, BindingResult bindingResult,
//                              Model model, RedirectAttributes redirectAttributes,
//                              Authentication authentication, HttpSession session,
//                              HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            logger.info("Validation errors were found while adding delivery info");
//            model.addAttribute("order", order);
//            model.addAttribute("validationErrors", bindingResult.getAllErrors());
//            return "account/order/search";
//        } else {
//            User user = (User) authentication.getPrincipal();
//            user = userService.getUserByEmail(user.getEmail()); //get data from db
//            List<Order> orders = new ArrayList<>();
//            if (order.getStatus().isEmpty()) {
//                orders = orderService.getAllOrdersByUser(user);
//            }else {
//                orders = orderService.getOrders(order.getStatus(), user);
//            }
//
//            session.setAttribute("orders", orders);
//            session.setAttribute("order", order);
//
////                redirectAttributes
////                        .addAttribute("id", newUser.getId())
////                        .addFlashAttribute("success", true);
//            return "redirect:/account/order";
//        }
//    }


    @PostMapping("/account/order/search")
    public String searchOrder(Model model,
                              @RequestParam(required = false) String productName,
                              @RequestParam(required = false) String open,
                              @RequestParam(required = false) String placed,
                              @RequestParam(required = false) String delivery,
                              @RequestParam(required = false) String closed,
                              HttpSession session) {

        List<Order> orders = new ArrayList<>();
        if (open == null && placed == null && delivery == null && closed == null) {
            orders = orderService.getAllOrders();
        } else {
            List<Order> orders1 = orderService.getOrdersByStatus(open);
            List<Order> orders2 = orderService.getOrdersByStatus(placed);
            List<Order> orders3 = orderService.getOrdersByStatus(delivery);
            List<Order> orders4 = orderService.getOrdersByStatus(closed);
            orders.addAll(orders1);
            orders.addAll(orders2);
            orders.addAll(orders3);
            orders.addAll(orders4);
        }

        session.setAttribute("orders", orders);
//            session.setAttribute("order", order);
        model.addAttribute("orders", orders);

        return "redirect:/account/order";
    }


}
