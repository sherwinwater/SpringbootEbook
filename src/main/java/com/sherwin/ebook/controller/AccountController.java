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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
    public String getBillingInfo(Model model, Authentication authentication) {
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
            accountService.addBilling(account, billing);
            return "redirect:/account/billing";
        }
    }

    @GetMapping("/account/delivery")
    public String getDeliveryInfo(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
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
            user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
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
            user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
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
        return "redirect:/book";
    }

    @GetMapping("/account/favorite")
    public String getFavorite(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
        Account account = user.getAccount();
        Favorite favorite = account.getFavorite();

        model.addAttribute("favorite", favorite);
        return "account/favorite";
    }


    @GetMapping("/account/favorite/delete/{id}")
    public String deleteBook(@PathVariable Long id, Authentication authentication, HttpSession session) {
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
        Account account = user.getAccount();
        Favorite favorite = account.getFavorite();
        accountService.deleteFavorite(account, favorite, id);
        return "redirect:/account/favorite";
    }

}
