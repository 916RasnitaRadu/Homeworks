package com.example.A2.api;

import com.example.A2.domain.Product;
import com.example.A2.domain.dto.ProductRequest;
import com.example.A2.domain.dto.ProductResponse;
import com.example.A2.domain.dto.ProductWithReviewDTO;
import com.example.A2.domain.dto.ReviewRequest;
import com.example.A2.service.ProductService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/product")
@ComponentScan({"com.example.A2.service.ProductService"} )
@RestController
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity findProductById(@PathVariable Long id)
    {
        Optional<Product> product = service.get(id);
        if (product.isEmpty())
        {
            return ResponseEntity.notFound().build();

        }
        ProductWithReviewDTO productDTO = new ProductWithReviewDTO();
        productDTO.setId(product.get().getId());
        productDTO.setName(product.get().getName());
        productDTO.setPrice(product.get().getPrice());
        productDTO.setColor(product.get().getColor());
        productDTO.setDescription(product.get().getDescription());
        productDTO.setCategory(product.get().getCategory());
        productDTO.setReviewList(
                product.get().getReviewsList().stream().map(review -> new ReviewRequest(review.getId(),
                        review.getCustomer().getId(), review.getProduct().getId(), review.getReviewText(), review.getCreatedAt(), review.getNumberLikes())).collect(Collectors.toList()));

        return ResponseEntity.ok(productDTO);
    }

    @GetMapping(path = "/")
    public List<ProductResponse> getAllProducts()
    {
        return service.getAll();
    }


    @PostMapping("/")
    public ProductResponse addProduct(@RequestBody ProductRequest product)
    {
        return service.addService(product);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProduct(@PathVariable Long id)
    {
        service.deleteService(id);
    }

    @PatchMapping(path = "/{id}")
    public void update(@PathVariable Long id,@RequestBody Product newProduct) {
        service.updateService(id,newProduct);
    }

    @GetMapping(path = "/filter/{price}")
    public List<Product> findProductsPriceHigherThan(@PathVariable double price)
    {
        return service.findProductsPriceHigherThanService(price);
    }
}
