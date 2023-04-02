package com.example.A2.service;

import com.example.A2.domain.Customer;
import com.example.A2.domain.Review;
import com.example.A2.domain.dto.CustomerRequest;
import com.example.A2.domain.dto.CustomerResponse;
import com.example.A2.domain.dto.CustomerWithReviewDTO;
import com.example.A2.domain.dto.ReviewRequest;
import com.example.A2.domain.mappers.CustomerMapper;
import com.example.A2.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService  {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;


    public CustomerWithReviewDTO get(Long id) {
        if (customerRepository.existsById(id))
        {
            Optional<Customer> customer = customerRepository.findById(id);

            if (customer.isEmpty()) return null;

            CustomerWithReviewDTO customerDTO = new CustomerWithReviewDTO();
            customerDTO.setId(customer.get().getId());
            customerDTO.setAge(customer.get().getAge());
            customerDTO.setFirstName(customer.get().getFirstName());
            customerDTO.setLastName(customer.get().getLastName());
            customerDTO.setAddress(customer.get().getAddress());
            customerDTO.setEmail(customer.get().getEmail());
            customerDTO.setReviewList(customer.get().getReviewList().stream().map(review -> new ReviewRequest(review.getId(),
                    review.getCustomer().getId(), review.getProduct().getId(), review.getReviewText(), review.getCreatedAt(), review.getNumberLikes())).collect(Collectors.toList()));
            return customerDTO;
        }
        return null;
    }


    public CustomerResponse addService(CustomerRequest customerRequest) {
        Customer customer = customerMapper.map(customerRequest);
        customerRepository.save(customer);
        return customerMapper.map(customer);
    }


    public List<CustomerResponse> getAll() {
        return customerMapper.map(customerRepository.findAll());
    }


    public void deleteService(Long id) {
        if (customerRepository.existsById(id))
        {
            customerRepository.deleteById(id);
        }
    }


    public void updateService(Long id, Customer entity) {
        Customer customer = customerRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("There is no such entity in the database"));

        customer.setFirstName(entity.getFirstName());
        customer.setLastName(entity.getLastName());
        customer.setAge(entity.getAge());
        customer.setEmail(entity.getEmail());
        customer.setAddress(entity.getAddress());
        customer.setReviewList(entity.getReviewList());

        customerRepository.save(customer);
    }

    public HashMap<Customer, List<Review>> getCustomerWithList(Long id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalStateException("There is no such entity in the database."));
        List<Review> reviews = customer.getReviewList();
        HashMap<Customer, List<Review>> result = new HashMap<>();
        result.put(customer, reviews);
        return result;
    }
}
