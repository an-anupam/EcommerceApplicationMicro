package com.ecommerce.order.service;

import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserRepsonse;
import com.ecommerce.order.interserviceClient.ProductServiceClient;
import com.ecommerce.order.interserviceClient.UserServiceClient;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import com.ecommerce.order.dto.CartItemRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

  private final CartItemRepository cartItemRepository;
  private final ProductServiceClient productServiceClient;
  private final UserServiceClient userServiceClient;

    public boolean addToCart(String userId, CartItemRequest request) {

            //Look for product
        ProductResponse productResponse = productServiceClient.getProductDetails(request.getProductId());

        if(productResponse == null){
            return false;
        }

        if(productResponse.getStockQuantity() < request.getQuantity())
            return false;


        UserRepsonse userRepsonse =  userServiceClient.getUserDetails(userId);
        if(userRepsonse == null)
            return false;

        CartItem exisitngCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if (exisitngCartItem != null) {
           // Update the quantity
            exisitngCartItem.setQuantity(exisitngCartItem.getQuantity() + request.getQuantity());
            exisitngCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(exisitngCartItem);
        }else {
            //create new cart Item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
        return true;
    }
//
    public boolean deleteItemFromCart(String userId, String productId) {

     CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if(cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;

    }
//
    public List<CartItem> fetchCartItems(String userId) {

        return cartItemRepository.findByUserId(userId);

    }
//

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
