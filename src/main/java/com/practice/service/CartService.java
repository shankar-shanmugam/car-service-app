package com.practice.service;


import org.springframework.http.ResponseEntity;

import com.practice.dtoresponse.CartResponse;
import com.practice.utility.ResponseStructure;

public interface CartService {

	ResponseEntity<ResponseStructure<CartResponse>> addServiceToCart(int carServiceId);

	ResponseEntity<ResponseStructure<CartResponse>> deleteServiceInCart(int carServiceId);

	ResponseEntity<ResponseStructure<CartResponse>> fetchAllInCart();


}
