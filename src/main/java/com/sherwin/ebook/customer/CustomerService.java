package com.sherwin.ebook.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(Customer customer){customerRepository.save(customer);}
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    public Customer get(Long id){return customerRepository.findCustomerById(id);}
    public void delete(Long id){customerRepository.deleteById(id);}

    public List<Customer> search(String content) {
        return customerRepository.findByFirstNameOrLastNameOrEmailIdContaining(content,content,content);
    }

}
