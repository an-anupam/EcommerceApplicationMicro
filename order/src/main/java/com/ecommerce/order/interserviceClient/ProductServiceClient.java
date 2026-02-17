package com.ecommerce.order.interserviceClient;


import com.ecommerce.order.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


@HttpExchange
public interface  ProductServiceClient {

   @GetExchange("/api/products/fetch/{id}")
   ProductResponse getProductDetails(@PathVariable String id);

}
