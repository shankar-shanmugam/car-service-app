package com.practice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dtoresponse.CartResponse;
import com.practice.service.CartService;
import com.practice.utility.ResponseStructure;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/carServices/{carServiceId}/carts")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ResponseStructure<CartResponse>> addServiceToCart ( @PathVariable int carServiceId) {
       return cartService.addServiceToCart(carServiceId);
        
    }
    
    @DeleteMapping("/carServices/{carServiceId}/carts")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ResponseStructure<CartResponse>> deleteServiceInCart ( @PathVariable int carServiceId) {
       return cartService.deleteServiceInCart(carServiceId);
        
    }
 
    @GetMapping("/carts/carServices")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ResponseStructure<CartResponse>> fetchAllCarts() {
        return cartService.fetchAllInCart();
    }
    
}

