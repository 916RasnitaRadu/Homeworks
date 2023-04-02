package com.example.A2.api;

import com.example.A2.domain.dto.ProductResponse;
import com.example.A2.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    public void setUp()
    {
        reviewService = Mockito.mock(ReviewService.class);
        reviewController = new ReviewController(reviewService);
    }

    @Test
    void getStatsProd() {
        HashMap<ProductResponse, Double> testMap = new HashMap<>();
        ProductResponse product1 = new ProductResponse();
        product1.setId(9L);
        product1.setPrice(50.9);
        product1.setName("Shaker cu bila");
        product1.setDescription("Shaker jmk cu bila");
        product1.setColor("negru");
        product1.setCategory("Accesorii");
        Double avg1 = 19.0;
        testMap.put(product1, avg1);

        ProductResponse product2 = new ProductResponse();
        product2.setPrice(175.9);
        product2.setId(5L);
        product2.setName("Hanorac");
        product2.setDescription("Hanorac bravo");
        product2.setColor("gri");
        product2.setCategory("Imbracaminte");
        Double avg2 = 96.0;
        testMap.put(product2, avg2);

        // (4L, "Hanorac", 190.9, "Hanorac bravo", "alb", "Imbracaminte")
        ProductResponse product3 = new ProductResponse();
        product3.setId(4L);
        product3.setPrice(190.9);
        product3.setName("Hanorac");
        product3.setDescription("Hanorac bravo");
        product3.setColor("alb");
        product3.setCategory("Imbracaminte");
        Double avg3 = 240.0;
        testMap.put(product3, avg3);

        // 3L, "BCAA", 80.9, "pastile sa dai pe gat","alb","suplimente"
        ProductResponse product4 = new ProductResponse();
        product4.setId(3L);
        product4.setPrice(190.9);
        product4.setName("80.9");
        product4.setDescription("pastile sa dai pe gat");
        product4.setColor("alb");
        product4.setCategory("suplimente");
        Double avg4 = 246.33333333333;
        testMap.put(product4, avg4);

        // 2L, "Creatina", 80.9, "creatina sa te faci mare", "alb", "suplimente"
        ProductResponse product5 = new ProductResponse();
        product5.setId(2L);
        product5.setPrice(80.9);
        product5.setName("Creatina");
        product5.setDescription("creatina sa te faci mare");
        product5.setColor("negru");
        product5.setCategory("suplimente");
        Double avg5 = 354.0;
        testMap.put(product5,avg5);

        //8L, "Shaker", 30.9, "Shaker jmk", "negru", "Accesorii"
        ProductResponse product6 = new ProductResponse();
        product6.setId(8L);
        product6.setPrice( 30.9);
        product6.setName("Shaker");
        product6.setDescription("Shaker jmk");
        product6.setColor("negru");
        product6.setCategory("Accesorii");
        Double avg6 = 500.0;
        testMap.put(product6,avg6);

        ProductResponse product7 = new ProductResponse();
        product7.setId(11L);
        product7.setPrice(189.9);
        product7.setName("CURIA");
        product7.setDescription("CURIA sa nu-ti rupi schinarea");
        product7.setColor("negru");
        product7.setCategory("Accesorii");
        Double avg7 = 762.0;
        testMap.put(product7, avg7);

        ProductResponse product8 = new ProductResponse();
        product8.setId(6L);
        product8.setPrice(200.9);
        product8.setName("Hanorac");
        product8.setDescription("Hanorac bravo");
        product8.setColor("albastru");
        product8.setCategory("Imbracaminte");
        Double avg8 = 784.0;
        testMap.put(product8,avg8);


        List<Map.Entry<ProductResponse, Double>> resultExpected = new ArrayList<>(testMap.entrySet());
        resultExpected.sort(Map.Entry.comparingByValue());

        when(reviewController.getStatsProd()).thenReturn(resultExpected);
        assertEquals(8, resultExpected.size());
        List<Map.Entry<ProductResponse, Double>> result = reviewController.getStatsProd();

        assertEquals(8, result.size());
        for (int i = 0; i < 8; i++)
        {
            assertEquals(resultExpected.get(i), result.get(i));
        }
    }
}