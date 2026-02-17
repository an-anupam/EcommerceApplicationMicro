package com.ecommerce.order.interserviceClient;

import com.ecommerce.order.dto.UserRepsonse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("/api/get-user/{id}")
     UserRepsonse getUserDetails(@PathVariable String id);

}
