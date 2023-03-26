package com.example.A2.api;

import com.example.A2.domain.dto.ReviewRequest;
import com.example.A2.domain.dto.ReviewResponse;
import com.example.A2.service.ReviewService;
import com.example.A2.domain.Review;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/review")
@ComponentScan({"com.example.A2.service.ReviewService"} )
@RestController
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}")
    public Optional<Review> findReviewById(@PathVariable Long id) { return service.get(id);}

    @GetMapping(path="/")
    public List<ReviewResponse> getAllReviews() { return service.getAll();}

    @PostMapping(path = "/")
    public void addReview(@RequestBody ReviewRequest review)
    {
        service.addService(review);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteReview(@PathVariable Long id) {
        service.deleteService(id);
    }

    @PatchMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody Review review) {
        service.updateService(id, review);
    }
}
