package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.repository.OrderRepository;
import com.sherwin.ebook.service.*;
import com.sherwin.ebook.utilities.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/admin")
    public String goAdminHome() {
        return "admin/home";
    }

    @GetMapping("/admin2")
    public String goAdminHome2() {
        return "admin/home2";
    }


    //    -----user-----
    @RequestMapping("/admin/user/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/view";
    }

    @GetMapping("/admin/user/update/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/updateUser";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@Valid User user, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes,
                             Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/user/updateUser";
        } else {
            userService.updateUser(user);
            return "redirect:/admin/user/view";
        }
    }


    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.delete(id);
        return "redirect:/admin/user/view";
    }

    @GetMapping("/admin/user/create")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/userform";
    }

    @PostMapping("/admin/user/save")
    public String saveUser(@Valid User user, BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userService.findAll());
            return "user/list";
        } else {
            userService.save(user);
            return "redirect:/admin/user/" + user.getId();
        }
    }

    @GetMapping("/admin/user/view")
    public String home(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/user/viewUsers";
    }

    @GetMapping("/admin/user/add")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "admin/user/addUser";
    }

    @PostMapping("/admin/user/add")
    public String registerNewUser(@Valid User user, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/user/addUser";
        } else {
            User newUser = userService.register(user);
            redirectAttributes
                    .addAttribute("id", newUser.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/admin/user/add";
        }
    }

    @PostMapping("/admin/user/changePrivilege/{id}")
    public String changePrivilege(Model model, @PathVariable Long id,
                                  @RequestParam(required = false) String roleUser,
                                  @RequestParam(required = false) String roleAdmin
    ) {
        User user = userService.getUserById(id);
        user.getRoles().clear();
        Role role1 = roleService.findByName(roleUser);
        Role role2 = roleService.findByName(roleAdmin);
        user.getRoles().add(role1);
        user.getRoles().add(role2);
        userService.save(user);

        return "redirect:/admin/user/update/{id}";
    }


    //------product------
    @GetMapping("/admin/product/add")
    public String addProductHome(Model model, Authentication authentication) {
        Book book = new Book();
        List<Category> categories = categoryService.getCategory();
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "admin/product/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addBook(@RequestParam String name, @RequestParam int inventory,
                          @RequestParam double price, @RequestParam String location,
                          @RequestParam String details, @RequestParam String category,
                          @RequestParam(value = "file") MultipartFile file, Model model) {

        String photoUrl = upload(file, model);
        Book book = new Book(name, price, "", location);
        book.setDetails(details);
        book.setInventory(inventory);
        book.setPhotoUrl(photoUrl);
        Category category1 = categoryService.getCategoryByName(category);
        book.setCategory(category1);
        bookService.save(book);
        return "redirect:/admin/product/add";
    }

    public String upload(@RequestParam(value = "file") MultipartFile file, Model model) {

        String filename = file.getOriginalFilename();
        File fileDir = UploadUtils.getImgDirFile();
        try {
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        filename = "/images/" + filename;
        model.addAttribute("filename", filename);
        return filename;
    }

//    @PostMapping("/admin/product/add")
//    public String addBook(@Valid Book book, BindingResult bindingResult,
//                          Model model, RedirectAttributes redirectAttributes,
//                          Authentication authentication,
//                          @RequestParam String category) {
//        if (bindingResult.hasErrors()) {
//            logger.info("Validation errors were found while adding a product");
//            logger.info(String.valueOf(bindingResult.getAllErrors()));
//            model.addAttribute("book", book);
//            model.addAttribute("validationErrors", bindingResult.getAllErrors());
//            return "admin/product/addProduct";
//        } else {
////            Category category1 = categoryService.getCategoryByName(category);
////            book.setCategory(category1);
//            bookService.save(book);
//            return "redirect:/admin/product/add";
//        }
//    }
//

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        return "admin/product/updateProduct";
    }

    @PostMapping("/admin/product/update")
    public String updateBook(@Valid Book book, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes,
                             Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("book", book);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/product/addProduct";
        } else {
            bookService.updateBook(book);
            return "redirect:/admin/product/add";
        }
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        bookService.delete(id);
        return "redirect:/admin/product/view";
    }

    @PostMapping("/admin/product/search")
    public String searchProduct(@Valid Book book, BindingResult bindingResult,
                                Model model, RedirectAttributes redirectAttributes,
                                Authentication authentication, HttpSession session,
                                HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while adding delivery info");
            model.addAttribute("book", book);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/product/search";
        } else {
            List<Book> books = bookService.findAllByNameAndLocation(book.getName(), book.getLocation());
//            List<Book> books = bookService.search(book.getName());

            model.addAttribute("books", books);
            model.addAttribute("book", book);

            return "admin/product/viewProducts";
        }
    }

    @GetMapping("/admin/product/view")
    public String getProduct(Model model, Authentication authentication,
                             HttpSession session) {
        Book book = new Book();
        List<Book> books = new ArrayList<>();
        model.addAttribute("books", books);
        model.addAttribute("book", book);
        return "admin/product/viewProducts";
    }

    //    order
    @GetMapping("/admin/order/view")
    public String getOrder(Model model, Authentication authentication,
                           HttpSession session) {

        Order order = (Order) session.getAttribute("order");
        List<Order> orders = (List<Order>) session.getAttribute("orders");
        String[] statuses = {"open", "placed", "delivery", "closed"};
        if (orders == null) {
            orders = new ArrayList<>();
        }
//        if (order == null) {
//            order = new Order();
//        }
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", statuses);
        return "admin/order/viewOrders";
    }

    @PostMapping("/admin/order/search")
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

        return "redirect:/admin/order/view";

    }

    @PostMapping("/admin/order/update")
    public String updateBook(Model model, @RequestParam String orderId,
                             @RequestParam String status) {
        Order order = orderService.getOrder(Long.parseLong(orderId));
        order.setStatus(status);
        orderService.save(order);

        return "redirect:/admin/order/view";

    }

    @GetMapping("/admin/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/admin/order/view";
    }

}
