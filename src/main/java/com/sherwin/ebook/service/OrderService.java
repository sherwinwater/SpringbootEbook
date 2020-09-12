package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.repository.CartRepository;
import com.sherwin.ebook.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Order getLastOrder(User user) {
        Optional<Order> optionalOrder = orderRepository.findFirstByUserOrderByIdDesc(user);
        return optionalOrder.get();
    }

    public Order getOpenOrder(String status, User user) {
        return orderRepository.findOrderByStatusAndUser(status, user);
    }

    public Order getOrder(Long id) {
        Order order = orderRepository.findOrderById(id);
        return order;
//        return orderRepository.findOrderById(id);
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
        order.addBooks(new HashSet<>(books));  //add books using new HashSet
        cart.getOrders().add(order);

        orderRepository.save(order);
    }

    public void placeOrder(User user, Cart cart, Order order) {
        order.setStatus("placed");
        orderRepository.save(order);

        Order order1 = new Order();
        order1.setStatus("open");
        order1.setUser(user);
        order1.setCart(cart);
        cart.getOrders().add(order1);
        user.getOrders().add(order1);

        Billing billing = new Billing();
        Payment payment = new Payment();
        billing.setPayment(payment);
        order1.setBilling(billing);
        Delivery delivery = new Delivery();
        order1.setDelivery(delivery);
        orderRepository.save(order1);

        Order order2 = getOpenOrder("open",user);
        order2.setBilling(null);
        order2.setDelivery(null);
//        order2.getBilling().setFirstName(order.getBilling().getFirstName());
//        order2.getBilling().setLastName(order.getBilling().getLastName());
//        order2.getBilling().setEmail(order.getBilling().getEmail());
//        order2.getBilling().setAddress(order.getBilling().getAddress());
//        order2.getBilling().setCountry(order.getBilling().getCountry());
//        order2.getBilling().setState(order.getBilling().getState());
//        order2.getBilling().setZip(order.getBilling().getZip());
//
//        order2.getDelivery().setFirstName(order.getDelivery().getFirstName());
//        order2.getDelivery().setLastName(order.getDelivery().getLastName());
//        order2.getDelivery().setEmail(order.getDelivery().getEmail());
//        order2.getDelivery().setAddress(order.getDelivery().getAddress());
//        order2.getDelivery().setCountry(order.getDelivery().getCountry());
//        order2.getDelivery().setState(order.getDelivery().getState());
//        order2.getDelivery().setZip(order.getDelivery().getZip());
//
//        order2.getBilling().getPayment().setCreditCardNumber(order.getBilling().getPayment().getCreditCardNumber());
//        order2.getBilling().getPayment().setFullName(order.getBilling().getPayment().getFullName());
//        order2.getBilling().getPayment().setExpiration(order.getBilling().getPayment().getExpiration());
//        order2.getBilling().getPayment().setPaymentType(order.getBilling().getPayment().getPaymentType());
//        order2.getBilling().getPayment().setCVV(order.getBilling().getPayment().getCVV());
//        order2.getBilling().getPayment().setCreatedBy(order.getBilling().getPayment().getCreatedBy());
//        order2.getBilling().getPayment().setCreationDate(order.getBilling().getPayment().getCreationDate());

        orderRepository.save(order2);

    }
}
