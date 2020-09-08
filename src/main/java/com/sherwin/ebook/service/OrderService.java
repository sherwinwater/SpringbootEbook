package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.Order;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.repository.CartRepository;
import com.sherwin.ebook.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private final double TAX_RATE = 0.175;
    private double deliverFeeRate;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void addOrder(User user, Cart cart, Order order) {
        order.setStatus("open");
        order.setTax(cart.getTotalPrice() * TAX_RATE);
        if (cart.getTotalPrice() < 50.00) {
            deliverFeeRate = 0.11;
        } else {
            deliverFeeRate = 0;
        }
        order.setDeliveryFee(cart.getTotalPrice() * deliverFeeRate);
        order.setTotalPrice(cart.getTotalPrice() * (1 + TAX_RATE + deliverFeeRate));
        order.addUser(user);
        order.setCart(cart);
        Set<Book> books = cart.getBooks();
        order.addBooks(new HashSet<>(books));
        orderRepository.save(order);

        cart.setOrder(order);
        cartRepository.save(cart);
    }
}
