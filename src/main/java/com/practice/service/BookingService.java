package com.practice.service;

import org.springframework.http.ResponseEntity;

import com.practice.dtoresponse.BookingResponse;
import com.practice.utility.ResponseStructure;

public interface BookingService {

	ResponseEntity<ResponseStructure<BookingResponse>> createBooking(int carId);
	
}
