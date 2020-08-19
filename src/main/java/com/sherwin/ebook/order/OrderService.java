package com.sherwin.ebook.order;

import com.sherwin.ebook.book.Book;
import com.sherwin.ebook.book.BookService;
import com.sherwin.ebook.customer.Customer;
import com.sherwin.ebook.customer.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(Order order) { orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> get(Customer customer) {
        return orderRepository.findCartByCustomer(customer);
    }

    public Order getByid(Long id) {
        return orderRepository.findCartById(id);
    }

}
