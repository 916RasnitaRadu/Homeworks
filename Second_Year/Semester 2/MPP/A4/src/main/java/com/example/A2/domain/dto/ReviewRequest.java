package com.example.A2.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Long id;

    @NotNull(message = "The ID of the Customer can not be null")
    private Long idCustomer;

    @NotNull(message = "The ID of the Product can not be null")
    private Long idProduct;

    @NotBlank(message = "Please enter the review")
    @Size(min=4, message = "The review should be at least 4 characters long")
    private String reviewText;

    private String createdAt;

    private Integer numberLikes;


}
