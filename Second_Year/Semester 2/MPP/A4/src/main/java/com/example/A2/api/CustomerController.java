package com.example.A2.api;

import com.example.A2.domain.Review;
import com.example.A2.domain.dto.*;
import com.example.A2.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        CustomerWithReviewDTO customerDTO = service.get(id);
        if (customerDTO == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping(path = "/")
    public List<CustomerResponse> getAllCustomers() {
        return service.getAll();
    }

    @PostMapping(path = "/")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody @Valid CustomerRequest customer) {
        CustomerResponse customerResponse = service.addService(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteService(id);
    }

    @PatchMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid Customer newCustomer) {
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
