package com.example.A2.api;

import com.example.A2.domain.Review;
import com.example.A2.domain.dto.*;
import com.example.A2.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.A2.domain.Customer;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;


    @GetMapping(path = "/{id}")
    public ResponseEntity findCustomerById(@PathVariable Long id)
    {
        Optional<Customer> customer = service.get(id);
        if (customer.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        CustomerWithReviewDTO customerDTO = new CustomerWithReviewDTO();
        customerDTO.setId(customer.get().getId());
        customerDTO.setAge(customer.get().getAge());
        customerDTO.setFirstName(customer.get().getFirstName());
        customerDTO.setLastName(customer.get().getLastName());
        customerDTO.setAddress(customer.get().getAddress());
        customerDTO.setEmail(customer.get().getEmail());
        customerDTO.setReviewList(customer.get().getReviewList().stream().map(review -> new ReviewRequest(review.getId(),
                review.getCustomer().getId(), review.getProduct().getId(), review.getReviewText(), review.getCreatedAt(), review.getNumberLikes())).collect(Collectors.toList()));
        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping(path = "/")
    public List<CustomerResponse> getAllCustomers() {
        return service.getAll();
    }

    @PostMapping(path = "/")
    public CustomerResponse addCustomer(@RequestBody CustomerRequest customer) {
        return service.addService(customer);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteService(id);
    }

    @PatchMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody Customer newCustomer) {
        service.updateService(id,newCustomer);
    }

    private ReviewRequest convertReviewDTO(Review review)
    {
        ReviewRequest reviewIdDTO = new ReviewRequest();
        reviewIdDTO.setId(review.getId());
        reviewIdDTO.setIdCustomer(review.getCustomer().getId());
        reviewIdDTO.setIdProduct(review.getProduct().getId());
        reviewIdDTO.setCreatedAt(review.getCreatedAt());
        reviewIdDTO.setReviewText(review.getReviewText());
        reviewIdDTO.setNumberLikes(review.getNumberLikes());
        return reviewIdDTO;
    }
}
