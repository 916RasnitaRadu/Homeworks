package com.example.A2.service;

import com.example.A2.domain.Customer;
import com.example.A2.domain.Product;
import com.example.A2.domain.Review;

import com.example.A2.domain.dto.ReviewRequest;
import com.example.A2.domain.dto.ReviewResponse;
import com.example.A2.domain.mappers.ReviewMapper;
import com.example.A2.repository.CustomerRepository;
import com.example.A2.repository.ProductRepository;
import com.example.A2.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final ReviewMapper reviewMapper;

    public Optional<Review> get(Long id) {
        if (reviewRepository.existsById(id)) return reviewRepository.findById(id);
        return Optional.empty();
    }


    public ReviewResponse addService(ReviewRequest reviewRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(reviewRequest.getIdCustomer());
        Optional<Product> optionalProduct = productRepository.findById(reviewRequest.getIdProduct());
        Review review = reviewMapper.map(reviewRequest);

        if (optionalProduct.isPresent()) review.setProduct(optionalProduct.get());
        else return null;
        if (optionalCustomer.isPresent()) review.setCustomer(optionalCustomer.get());
        else return null;

        reviewRepository.save(review);

        customerRepository.save(optionalCustomer.get());
        productRepository.save(optionalProduct.get());
        return reviewMapper.map(review);
    }


    public List<ReviewResponse> getAll() {
        return reviewMapper.map(reviewRepository.findAll());
    }


    public void deleteService(Long id) {
        if (reviewRepository.existsById(id))
        {
            reviewRepository.deleteById(id);
        }
    }


    public void updateService(Long id, Review entity) {
        Review review = reviewRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("There is no such entity in the database"));

        //review.setClientId(entity.getClientId());
        review.setReviewText(entity.getReviewText());
      //  review.setProductId(entity.getProductId());
        review.setCreatedAt(entity.getCreatedAt());
        review.setNumberLikes(entity.getNumberLikes());

        reviewRepository.save(review);
    }
}
