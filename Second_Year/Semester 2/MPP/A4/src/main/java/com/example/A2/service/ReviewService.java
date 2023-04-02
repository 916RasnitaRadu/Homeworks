package com.example.A2.service;

import com.example.A2.domain.Customer;
import com.example.A2.domain.Product;
import com.example.A2.domain.Review;

import com.example.A2.domain.dto.CustomerResponse;
import com.example.A2.domain.dto.ProductResponse;
import com.example.A2.domain.dto.ReviewRequest;
import com.example.A2.domain.dto.ReviewResponse;
import com.example.A2.domain.mappers.CustomerMapper;
import com.example.A2.domain.mappers.ProductMapper;
import com.example.A2.domain.mappers.ReviewMapper;
import com.example.A2.repository.CustomerRepository;
import com.example.A2.repository.ProductRepository;
import com.example.A2.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final ReviewMapper reviewMapper;

    private final ProductMapper productMapper;
    private final CustomerMapper customerMapper;

    public ReviewResponse get(Long id) {
        if (reviewRepository.existsById(id))
        {
            Review review = reviewRepository.findById(id).get();
            return mapReviewResponse(review);
        }
        return null;
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

        return mapReviewResponse(review);
    }


    public List<ReviewResponse> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Review r : reviews)
        {
            ReviewResponse reviewResponse = mapReviewResponse(r);
            reviewResponses.add(reviewResponse);
        }
        return reviewResponses;
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

    private ReviewResponse mapReviewResponse(Review review)
    {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setIdCustomer(review.getCustomer().getId());
        reviewResponse.setIdProduct(review.getProduct().getId());
        reviewResponse.setReviewText(review.getReviewText());
        reviewResponse.setCreatedAt(review.getCreatedAt());
        reviewResponse.setNumberLikes( review.getNumberLikes());
        return reviewResponse;
    }

    // show all the products ordered by the average of their review likes
    // for each product => avg(reviewLikes)
    public List<Map.Entry<ProductResponse, Double>> getStatisticalReportProducts()
    {
        List<Product> products = productRepository.findAll();
        HashMap<ProductResponse, Double> result = new HashMap<>();

        List<Review> reviews = reviewRepository.findAll();
        for (Product p : products)
        {
            double s=0,count=0;
            for (Review r : reviews)
            {
                if (Objects.equals(r.getProduct().getId(), p.getId())) {
                    s += r.getNumberLikes();
                    count++;
                }
            }
            ProductResponse pr = productMapper.map(p);
            if (s > 0 && count > 0)
            {
                Double avg = s / count;
                result.put(pr, avg);
            }
        }
        List<Map.Entry<ProductResponse, Double>> rez = sort(result);
        return rez;
    }


    // how all the customers ordered by the average of their review likes
    public List<Map.Entry<CustomerResponse, Double>> getStatisticalReportCustomers()
    {
        List<Customer> customers = customerRepository.findAll();
        HashMap<CustomerResponse, Double> result = new HashMap<>();

        List<Review> reviews = reviewRepository.findAll();
        for (Customer customer : customers)
        {
            double sum=0,count=0;
            for (Review review : reviews)
            {
                if (Objects.equals(review.getCustomer().getId(), customer.getId())) {
                    sum += review.getNumberLikes();
                    count++;
                }
            }
            CustomerResponse customerResponse = customerMapper.map(customer);
            if (sum > 0 && count > 0)
            {
                Double avg = sum / count;
                result.put(customerResponse, avg);
            }
        }
        List<Map.Entry<CustomerResponse, Double>> entryList = new ArrayList<>(result.entrySet());
        entryList.sort(Map.Entry.comparingByValue());
        return entryList;
    }

    private List<Map.Entry<ProductResponse, Double>> sort(HashMap<ProductResponse, Double> map) {
        List<Map.Entry<ProductResponse, Double>> nlist = new ArrayList<>(map.entrySet());
        nlist.sort(Map.Entry.comparingByValue());

        return nlist;
    }
}
