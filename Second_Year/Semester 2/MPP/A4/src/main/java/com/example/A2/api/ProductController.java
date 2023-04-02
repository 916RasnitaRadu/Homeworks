package com.example.A2.api;

import com.example.A2.domain.Product;
import com.example.A2.domain.dto.ProductRequest;
import com.example.A2.domain.dto.ProductResponse;
import com.example.A2.domain.dto.ProductWithReviewDTO;
import com.example.A2.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/product")
@RestController
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWithReviewDTO> findProductById(@PathVariable Long id)
    {
        Optional<ProductWithReviewDTO> productDTO = service.get(id);
        if (productDTO.isEmpty())
        {
            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(productDTO.get());
    }

    @GetMapping(path = "/")
    public List<ProductResponse> getAllProducts()
    {
        return service.getAll();
    }



    @PostMapping("/")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest product)
    {
        ProductResponse addedProduct = service.addService(product);
        return new ResponseEntity<ProductResponse>(addedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProduct(@PathVariable Long id)
    {
        service.deleteService(id);
    }

    @PatchMapping(path = "/{id}")
    public void update(@PathVariable Long id,@RequestBody @Valid Product newProduct) {
        service.updateService(id,newProduct);
    }

    @GetMapping(path = "/filter/{price}")
    public List<ProductResponse> findProductsPriceHigherThan(@PathVariable double price)
    {
        return service.findProductsPriceHigherThanService(price);
    }
}
